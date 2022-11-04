package acme.critical.module.visual;

import acme.critical.module.Mod;
import acme.critical.mixin.SimpleOptionMixin;

public class Nightvision extends Mod {
    public static boolean nightvisionEnabled = false;

    public Nightvision() {
        super("Nightvision", "Dark b gone", Category.VISUAL);
    }

    public static void setGamma(double gamma) {
    ((SimpleOptionMixin) (Object) mc.options.getGamma()).forceSetValue(gamma);
    }

    @Override
    public void onEnable() {
        nightvisionEnabled = true;
        setGamma(255.0f);
        super.onTick();
    }

    @Override
    public void onDisable() {
        nightvisionEnabled = false;
        setGamma(1.0f);
        super.onDisable();
    }
}
