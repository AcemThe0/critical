package acme.critical.module.movement;

import acme.critical.module.Mod;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.hit.BlockHitResult;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Clicktp extends Mod {
    public NumberSetting distance = new NumberSetting("Distance", 0, 20, 6, 1);
    public ModeSetting mode = new ModeSetting("Mode", "Teleport", "Teleport", "Packet");

    public Clicktp() {
        super("ClickTP", "Teleports you on the block you click!", Category.MOVEMENT);
        addSettings(distance, mode, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        if (isEnabled() && mc.options.useKey.isPressed()) {
            HitResult hitres = mc.player.raycast(distance.getValueInt(), 0.05f, false);

            if (hitres.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) hitres).getBlockPos();
                Direction blockSide = ((BlockHitResult) hitres).getSide();

                VoxelShape shape = mc.world.getBlockState(blockPos).getCollisionShape(mc.world, blockPos);
                double height = shape.isEmpty() ? 1 : shape.getMax(Direction.Axis.Y);

                switch(mode.getMode()) {
                    case "Teleport":
                        mc.player.setPosition(blockPos.getX()+0.5+blockSide.getOffsetX(), blockPos.getY()+height, blockPos.getZ()+0.5+blockSide.getOffsetZ());
                    break;
                    case "Packet":
                        mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(blockPos.getX()+0.5+blockSide.getOffsetX(), blockPos.getY()+height, blockPos.getZ()+0.5+blockSide.getOffsetZ(), true));
                        mc.player.updatePosition(blockPos.getX()+0.5+blockSide.getOffsetX(), blockPos.getY()+height, blockPos.getZ()+0.5+blockSide.getOffsetZ());
                    break;
                }
            }

        }
    }
}