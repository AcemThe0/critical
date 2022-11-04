package acme.critical.module.movement;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;

public class Flight extends Mod {
    public ModeSetting mode = new ModeSetting("Mode", "Velocity", "Velocity", "Saki", "Moon");
    public NumberSetting speed = new NumberSetting("Speed", 0.0, 5, 0.4, 0.1);

    public Flight() {
        super("Flight", "Allows you to fly.", Category.MOVEMENT);
        addSettings(mode, speed, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        switch(mode.getMode()) {
            case "Velocity":
                mc.player.setVelocity(0, 0, 0);
                mc.player.airStrafingSpeed = speed.getValueFloat();
                if (mc.options.jumpKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat(), 0);
                if (mc.options.sneakKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat()*-1, 0);
            break;
            case "Saki":
                if(mc.options.jumpKey.isPressed()) mc.player.jump();
            break;
            case "Moon":
                mc.player.setNoGravity(true);
            break;
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        mc.player.setNoGravity(false);
    }

}
