package acme.critical.utils;

import net.minecraft.entity.Entity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;

public class FakePlayer extends OtherClientPlayerEntity {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public FakePlayer() {
        super(MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getGameProfile(), MinecraftClient.getInstance().player.getPublicKey());

        copyPositionAndRotation(mc.player);
        prevYaw = getYaw();
        prevPitch = getPitch();
        headYaw = mc.player.headYaw;
        bodyYaw = mc.player.bodyYaw;
        capeX = getX();
        capeY = getY();
        capeZ = getZ();
        setPose(mc.player.getPose());

        spawn();
    }

    public void spawn()
    {
        mc.world.addEntity(getId(), this);
    }

    public void despawn()
    {
        discard();
    }

}
