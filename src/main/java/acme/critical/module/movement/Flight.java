package acme.critical.module.movement;

import acme.critical.module.Mod;
import net.minecraft.entity.Entity;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Flight extends Mod {
    public ModeSetting mode = new ModeSetting("Mode", "Velocity", "Velocity", "Saki", "Boat");
    public NumberSetting speed = new NumberSetting("Speed", 0.0, 5, 0.4, 0.1);
    public BooleanSetting antiKick = new BooleanSetting("AntiKick", true);
    double oldY;
    int airTicks;

    public Flight() {
        super("Flight", "Allows you to fly.", Category.MOVEMENT);
        addSettings(mode, speed, antiKick, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        if (antiKick.isEnabled() && mc.player.getY() >= oldY-0.04d) {
            airTicks += 1;
        }

        oldY = mc.player.getY();
        switch(mode.getMode()) {
            case "Velocity":
                mc.player.setVelocity(0, 0, 0);
                mc.player.airStrafingSpeed = speed.getValueFloat();
                if (mc.options.jumpKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat(), 0);
                if (mc.options.sneakKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat()*-1, 0);
            break;
            case "Saki":
                if (mc.options.jumpKey.isPressed()) mc.player.jump();
            break;
            case "Boat":
                if(mc.player.hasVehicle()) {
                    Entity boat = mc.player.getVehicle();
                    if (mc.options.jumpKey.isPressed()) boat.addVelocity(0, speed.getValueFloat(), 0);
                }
            break;
        }

        if (antiKick.isEnabled() && airTicks > 20) {
            mc.player.addVelocity(0, -0.04, 0);
            airTicks = 0;
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        mc.player.setNoGravity(false);
    }

}
