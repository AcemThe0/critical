package acme.critical.module.movement;

import acme.critical.module.Mod;
import net.minecraft.util.math.Vec3d;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Mhop extends Mod {
    private ModeSetting mode = new ModeSetting("Mode", "Mhop", "Mhop", "Strafe");
    private NumberSetting speed = new NumberSetting("Speed", 0, 5, 0.1, 0.1);
    private ModeSetting jumpMd = new ModeSetting("Jump", "Vanilla", "Vanilla","Velocity");
    private BooleanSetting jump = new BooleanSetting("AutoJump", true);
    private NumberSetting jumpStr = new NumberSetting("Height", 0, 5, 0.5, 0.1);

    public Mhop() {
        super("Hop", "MonkeyHop!", Category.MOVEMENT);
        addSettings(mode, speed, jumpMd, jump, jumpStr, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        switch (mode.getMode()) {
            case "Mhop":
	        // broken by removal of airStrafingSpeed
	        /*
                mc.player.airStrafingSpeed = speed.getValueFloat();
                    if (jump.isEnabled() && (mc.player.forwardSpeed != 0 && mc.player.isOnGround() || mc.player.sidewaysSpeed != 0 && mc.player.isOnGround())) {
                        if (jumpMd.getMode() == "Vanilla") mc.player.jump();
                        if (jumpMd.getMode() == "Velocity") mc.player.addVelocity(0, jumpStr.getValueFloat(), 0);
                        }
                super.onTick();
		*/
            break;
            case "Strafe":
                if (mc.player.forwardSpeed != 0 || mc.player.sidewaysSpeed != 0) {
                    mc.player.setVelocity(new Vec3d(0, mc.player.getVelocity().y, 0));
                    mc.player.updateVelocity(speed.getValueFloat(), new Vec3d(mc.player.sidewaysSpeed, 0, mc.player.forwardSpeed));

                    double vel = Math.abs(mc.player.getVelocity().getX()) + Math.abs(mc.player.getVelocity().getZ());
                    if (jump.isEnabled() && vel >= 0.12 && mc.player.isOnGround()) {
                        mc.player.updateVelocity(vel >= 0.3 ? 0.0f : 0.15f, new Vec3d(mc.player.sidewaysSpeed, 0, mc.player.forwardSpeed));
                        if (jumpMd.getMode() == "Vanilla") mc.player.jump();
                        if (jumpMd.getMode() == "Velocity") mc.player.addVelocity(0, jumpStr.getValueFloat(), 0);
                    }
                }
            break;
        }
    }
}
