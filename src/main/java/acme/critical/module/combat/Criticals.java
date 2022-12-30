package acme.critical.module.combat;

import acme.critical.Critical;
import acme.critical.module.Mod;
import net.minecraft.util.hit.HitResult;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import acme.critical.event.events.EventPacket;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.event.eventbus.CriticalSubscribe;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public class Criticals extends Mod {
    private ModeSetting mode = new ModeSetting("Type", "Packet", "Packet", "Jump", "MiniJump");

    public Criticals() {
        super("Criticals", "Attempts to deal critical hits.", Category.COMBAT);
        addSettings(mode, new KeybindSetting("Key", 0));
    }
    
    @CriticalSubscribe
    public void sendPacket(EventPacket.Send event) {
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket && mc.player.isOnGround()) {
            doCrit(mode.getMode());
        }
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
    }

    private void doCrit(String mode) {
        switch(mode) {
            case "Packet":
                double x = mc.player.getX();
                double y = mc.player.getY();
                double z = mc.player.getZ();
                doPos(x, y+0.0625D, z, true);
                doPos(x, y, z, false);
                doPos(x, y+1.1E-5D, z, false);
                doPos(x, y, z, false);
            break;

            case "Jump":
                mc.player.jump();
            break;

            case "MiniJump":
                mc.player.addVelocity(0, 0.1, 0);
                mc.player.fallDistance = 0.1f;
                mc.player.setOnGround(false);
            break;
        }
    }

    public void doPos(double x, double y, double z, boolean onGround) {
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround));
    }

}
