package acme.critical.module.visual;

import acme.critical.Critical;
import acme.critical.module.Mod;
import net.minecraft.util.math.Vec3d;
import acme.critical.utils.FakePlayer;
import net.minecraft.entity.EntityPose;
import acme.critical.event.events.EventPacket;
import acme.critical.event.events.EventClientMove;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.event.eventbus.CriticalSubscribe;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;

public class Freecam extends Mod {
    private NumberSetting speed = new NumberSetting("Speed", 0.0, 5, 0.5, 0.1);

    FakePlayer fakeplayer;
    private double[] playerPos;
    private float[] playerRot;
    
    public Freecam() {
        super("Freecam", "Classic out of body experience!", Category.VISUAL);
        addSettings(speed, new KeybindSetting("Key", 0));
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
        fakeplayer = new FakePlayer();
        fakeplayer.spawn();

        mc.chunkCullingEnabled = false;

        playerPos = new double[] { mc.player.getX(), mc.player.getY(), mc.player.getZ() };
        playerRot = new float[] { mc.player.getYaw(), mc.player.getPitch() };
    }

    @Override
    public void onTick() {
        if (mc.cameraEntity.isInsideWall()) mc.getCameraEntity().noClip = true;
        
        mc.player.setVelocity(0, 0, 0);
        mc.player.setPose(EntityPose.STANDING);

        if (mc.player.forwardSpeed != 0 || mc.player.sidewaysSpeed != 0) {
            mc.player.updateVelocity(speed.getValueFloat(), new Vec3d(mc.player.sidewaysSpeed, 0, mc.player.forwardSpeed));
        }
        if (mc.options.jumpKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat(), 0);
        if (mc.options.sneakKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat()*-1, 0);

    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);

        mc.chunkCullingEnabled = true;
        mc.player.noClip = false;

        mc.player.refreshPositionAndAngles(playerPos[0], playerPos[1], playerPos[2], playerRot[0], playerRot[1]);
        mc.player.setVelocity(Vec3d.ZERO);

        fakeplayer.despawn();
    }

    @CriticalSubscribe
    public void sendPacket(EventPacket.Send event) {
        if (event.getPacket() instanceof PlayerMoveC2SPacket || event.getPacket() instanceof ClientCommandC2SPacket) {
            event.setCancelled(true);
        }
    }

    @CriticalSubscribe
    public void onClientMove(EventClientMove event) {
        mc.player.noClip = true;
    }
}
