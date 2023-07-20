package acme.critical.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotUtils {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static float getYawToEnt(Entity entity) {
        return mc.player.getYaw() + MathHelper.wrapDegrees(
                                        (float)Math.toDegrees(Math.atan2(
                                            entity.getZ() - mc.player.getZ(),
                                            entity.getX() - mc.player.getX()
                                        )) -
                                        90f - mc.player.getYaw()
                                    );
    }

    public static float getPitchToEnt(Entity entity, boolean Feet) {
        double y;
        if (Feet) {
            y = entity.getY(); // So no head?
        } else {
            y = entity.getEyeY();
        }

        double diffX = entity.getX() - mc.player.getX();
        double diffY = y - (mc.player.getY() +
                            mc.player.getEyeHeight(mc.player.getPose()));
        double diffZ = entity.getZ() - mc.player.getZ();
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        return mc.player.getPitch() +
            MathHelper.wrapDegrees(
                (float)-Math.toDegrees(Math.atan2(diffY, diffXZ)) -
                mc.player.getPitch()
            );
    }

    public static float angleDistance(Entity entity, boolean feet) {
        float eYaw = Math.abs(getYawToEnt(entity) - mc.player.getYaw());
        float ePitch =
            Math.abs(getPitchToEnt(entity, feet) - mc.player.getPitch());
        return eYaw + ePitch;
    }
}
