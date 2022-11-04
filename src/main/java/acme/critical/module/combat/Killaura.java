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
    public BooleanSetting lock = new BooleanSetting("Lock", false);
    public BooleanSetting attack = new BooleanSetting("Attack", true);
    public BooleanSetting players = new BooleanSetting("JustPlayers", true);
    public NumberSetting range = new NumberSetting("Range", 1, 10, 4, 1);
    public ModeSetting rotate = new ModeSetting("Rotate", "Snap", "Snap", "None");
    public ModeSetting speed = new ModeSetting("Speed", "Cooldwn", "Cooldwn", "Spam");

    public Killaura() {
        super("Killaura", "Attack entities in a radius.", Category.COMBAT);
        addSettings(range, speed, attack, players, rotate, lock);
        addSetting(new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        if (this.isEnabled() && mc.world != null && mc.world.getEntities() != null) {
            List<Entity> targets = new ArrayList<>();
            Entity target;

            for (Entity entity : mc.world.getEntities()) {
                if (entity instanceof LivingEntity && entity != mc.player && !FriendsUtils.friends.contains(entity.getEntityName()) && mc.player.distanceTo(entity) <= range.getValueInt()) {
                    if (players.isEnabled() && !entity.isPlayer()) {;}
                    else {targets.add(entity);}
                }
                targets.sort(Comparator.comparingDouble(ent -> mc.player.distanceTo(entity)));
            }

            if (targets.size() >= 1) {
                target = targets.get(0);
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
                            if (mc.player.getAttackCooldownProgress(0.5f) == 1) {attack(mc.player, target);}
                        break;
                        case "Spam":
                            attack(mc.player, target);
                        break;
                    }
                }
            }
        }
    super.onTick();
    }

    public void attack(Entity player, Entity target) {
        mc.interactionManager.attackEntity(mc.player, target); //Attack
        mc.player.swingHand(Hand.MAIN_HAND); //Visually swing hand
    }

}
