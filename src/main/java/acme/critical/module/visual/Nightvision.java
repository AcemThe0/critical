package acme.critical.module.visual;

import acme.critical.mixin.SimpleOptionMixin;
import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;

public class Nightvision extends Mod {
    public static double origGamma;
    public static boolean nvEnabled;

    public Nightvision() {
        super("Nightvision", "Dark b gone!", Category.VISUAL);
        addSetting(new KeybindSetting("Key", 0));
    }

    public static void setGamma(double gamma) {
        ((SimpleOptionMixin)(Object)mc.options.getGamma()).forceSetValue(gamma);
    }

    @Override
    public void onEnable() {
        nvEnabled = true;
        origGamma = mc.options.getGamma().getValue();
        setGamma(255.0f);
    }

    @Override
    public void onDisable() {
        nvEnabled = false;
        setGamma(origGamma);
    }
}
