package acme.critical.utils;

import net.minecraft.entity.Entity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;

public class FakePlayer extends OtherClientPlayerEntity {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public FakePlayer() {
        super(MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getGameProfile());

        copyPositionAndRotation(mc.player);
        getInventory().clone(mc.player.getInventory());
        copyPlayerModel(mc.player, this);
        
        headYaw = mc.player.headYaw;
        bodyYaw = mc.player.bodyYaw;
        capeX = getX();
        capeY = getY();
        capeZ = getZ();
        
        setPose(mc.player.getPose());
    }

    public void spawn()
    {
        mc.world.addEntity(getId(), this);
    }

    public void despawn()
    {
        discard();
    }

    private void copyPlayerModel(Entity from, Entity to)
    {
        DataTracker fromTracker = from.getDataTracker();
        DataTracker toTracker = to.getDataTracker();

        Byte playerModel = fromTracker.get(PlayerEntity.PLAYER_MODEL_PARTS);
        toTracker.set(PlayerEntity.PLAYER_MODEL_PARTS, playerModel);
    }

}
