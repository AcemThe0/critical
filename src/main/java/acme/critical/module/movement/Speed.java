package acme.critical.module.movement;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;

public class Speed extends Mod {
    public static NumberSetting multiplier =
        new NumberSetting("Speed", 0.1, 5, 1.5, 0.1);
    static boolean enabled;

    public Speed() {
        super("GameSpeed", "Changes the speed of the game.", Category.MOVEMENT);
        addSettings(multiplier, new KeybindSetting("Key", 0));
    }

    @Override
    public void onEnable() {
        enabled = true;
    }

    @Override
    public void onDisable() {
        enabled = false;
    }

    public static double getMultiplier() {
        return enabled ? multiplier.getValueFloat() : 1;
    }
}
