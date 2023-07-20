package acme.critical.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.Vec3d;

import acme.critical.mixin.MinecraftClientAccessor;

public class MiscUtils {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void attack(boolean pressed) {
        mc.options.attackKey.setPressed(pressed);
        // Causes application 2 stack overflow on code execution
        // if (pressed) ((MinecraftClientAccessor) (Object) mc).doAttack();
    }

    public static void use(boolean pressed) {
        if (!pressed)
            mc.options.useKey.timesPressed = 0;
        mc.options.useKey.setPressed(pressed);
    }

    public static void setAirStrafeSpeed(float speed) {
        if (!mc.player.isOnGround()) {
            mc.player.updateVelocity(
                speed,
                new Vec3d(mc.player.sidewaysSpeed, 0, mc.player.forwardSpeed)
            );
        }
    }
}
