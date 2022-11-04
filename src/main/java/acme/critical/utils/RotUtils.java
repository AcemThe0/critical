package acme.critical.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.MinecraftClient;

public class RotUtils {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static float getYawToEnt(Entity entity) {
        return mc.player.getYaw() + MathHelper.wrapDegrees((float) Math.toDegrees(Math.atan2(entity.getZ() - mc.player.getZ(), entity.getX() - mc.player.getX())) - 90f - mc.player.getYaw());
    }

    public static float getPitchToEnt(Entity entity) {
        double y = entity.getEyeY();

        double diffX = entity.getX() - mc.player.getX();
        double diffY = y - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
        double diffZ = entity.getZ() - mc.player.getZ();
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        return mc.player.getPitch() + MathHelper.wrapDegrees((float) - Math.toDegrees(Math.atan2(diffY, diffXZ)) - mc.player.getPitch());
    }
}
