package acme.critical.module.movement;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;

public class Clicktp extends Mod {
    private NumberSetting distance = new NumberSetting("Distance", 0, 20, 6, 1);

    public Clicktp() {
        super(
            "ClickTP", "Teleports you on the block you click!",
            Category.MOVEMENT
        );
        addSettings(distance, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        if (isEnabled() && mc.options.useKey.isPressed()) {
            HitResult hitres =
                mc.player.raycast(distance.getValueInt(), 0.05f, false);

            if (hitres.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult)hitres).getBlockPos();
                Direction blockSide = ((BlockHitResult)hitres).getSide();

                VoxelShape shape =
                    mc.world.getBlockState(blockPos).getCollisionShape(
                        mc.world, blockPos
                    );
                double height =
                    shape.isEmpty() ? 1 : shape.getMax(Direction.Axis.Y);

                mc.player.networkHandler.sendPacket(
                    (Packet) new PlayerMoveC2SPacket.PositionAndOnGround(
                        blockPos.getX() + 0.5 + blockSide.getOffsetX(),
                        blockPos.getY() + height,
                        blockPos.getZ() + 0.5 + blockSide.getOffsetZ(), true
                    )
                );
                mc.player.updatePosition(
                    blockPos.getX() + 0.5 + blockSide.getOffsetX(),
                    blockPos.getY() + height,
                    blockPos.getZ() + 0.5 + blockSide.getOffsetZ()
                );
            }
        }
    }
}
