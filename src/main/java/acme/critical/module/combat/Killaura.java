package acme.critical.module.combat;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.util.Hand;
import acme.critical.module.Mod;
import net.minecraft.entity.Entity;
import acme.critical.utils.RotUtils;
import acme.critical.utils.MathUtils;
import net.minecraft.entity.EntityType;
import acme.critical.utils.FriendsUtils;
import net.minecraft.entity.LivingEntity;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Killaura extends Mod {
    private NumberSetting range = new NumberSetting("Range", 1, 10, 4, 0.1);
    private ModeSetting speed = new ModeSetting("Speed", "Cooldwn", "Cooldwn", "Spam");
    private ModeSetting rotate = new ModeSetting("Rotate", "Snap", "Snap", "None");
    private ModeSetting priority = new ModeSetting("Priority", "Angle", "Angle", "Health");
    private BooleanSetting attack = new BooleanSetting("Attack", true);
    private BooleanSetting lock = new BooleanSetting("Lock", false);
    private BooleanSetting players = new BooleanSetting("JustPlayers", true);
    private BooleanSetting MoreHuman = new BooleanSetting("MoreHuman", true);

    public Entity currentTarget;

    public Killaura() {
        super("Killaura", "Attack entities in a radius.", Category.COMBAT);
        addSettings(range, speed, rotate, priority, attack, lock, players, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        if (this.isEnabled() && mc.world != null && mc.world.getEntities() != null) {
            currentTarget = null;
            List<Entity> targets = new ArrayList<>();
            Entity target;

            sortKA(targets);
            if (targets.size() >= 1) {
                target = targets.get(0);

                currentTarget = target; // used by esp

                if (!(rotate.getMode() == "None")) {
                    float yaw = RotUtils.getYawToEnt(target);
                    float pitch = RotUtils.getPitchToEnt(target);
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, mc.player.isOnGround()));

                    if (lock.isEnabled()) {
                        mc.player.setPitch(pitch);
                        mc.player.setYaw(yaw);
                    }
                    if (rotate.getMode() == "Snap") {
                        mc.player.setHeadYaw(yaw);
                        mc.player.setBodyYaw(yaw);
                    }
                }

                if (attack.isEnabled()) {
                    switch(speed.getMode()) {
                        case "Cooldwn":
                            if (mc.player.getAttackCooldownProgress(0.5f) == 1) {attack(target);}
                        break;
                        case "Spam":
                            attack(target);
                        break;
                    }
                }
            }
        }
    super.onTick();
    }

    private void sortKA(List<Entity> targets) {
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity && entity != mc.player && !FriendsUtils.friends.contains(entity.getEntityName()) && mc.player.distanceTo(entity) <= range.getValueFloat()) {
                if (players.isEnabled() && !entity.isPlayer()) {;}
                else {targets.add(entity);}
            }
        }
        targets.sort(Comparator.comparingDouble(ent -> mc.player.distanceTo(ent)));
        switch (priority.getMode()) {
            case "Health":
                targets.sort(Comparator.comparingDouble(ent -> ((LivingEntity)ent).getHealth()));
            break;
            case "Angle":
                targets.sort(Comparator.comparingDouble(ent -> RotUtils.angleDistance(ent)));
            break;
        }
    }

    public void attack(Entity target) {
        mc.interactionManager.attackEntity(mc.player, target); //Attack
        mc.player.swingHand(Hand.MAIN_HAND); //Visually swing hand
    }

    public Entity getCurrentTarget() {
        return currentTarget;
    }
}
