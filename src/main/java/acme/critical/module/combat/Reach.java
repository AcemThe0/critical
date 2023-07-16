package acme.critical.module.combat;

import acme.critical.Critical;
import net.minecraft.util.Hand;
import acme.critical.module.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.hit.EntityHitResult;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;

public class Reach extends Mod {
    private NumberSetting stepsize = new NumberSetting("StepSize", 0.5, 8.5, 7.0, 0.5);

    public Reach() {
        super("Reach", "Extends the range of attacks", Category.COMBAT);
        addSettings(stepsize, new KeybindSetting("Key", 0));
    }
    

    @Override
    public void onTick() {
        if (mc.options.useKey.isPressed() && (mc.crosshairTarget instanceof EntityHitResult)) {
            mc.options.useKey.setPressed(false);
                Entity target = ((EntityHitResult)mc.crosshairTarget).getEntity();

                Vec3d start = new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ());
                Vec3d end = new Vec3d(target.getX(), target.getY(), target.getZ());
                teleportTo(start, end);
                mc.interactionManager.attackEntity(mc.player, target);
                mc.player.swingHand(Hand.MAIN_HAND);
                teleportTo(end, start);
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    void teleportTo(Vec3d start, Vec3d end) {
        double tickDistance = 5.5;
        double distance = Math.ceil(start.distanceTo(end) / tickDistance);
        for (int step = 1; step <= distance; step++) {
            mc.player.setPosition(start.lerp(end, step/distance));
        Critical.logger.info("Step" + step + ": " + start.lerp(end, step/distance));
        }
    }
}
