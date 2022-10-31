package acme.critical.module.client;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;

public class Watermark extends Mod {
    public ModeSetting mode = new ModeSetting("Mode", "Basic", "Basic", "+Version");

    public Watermark() {
        super("Watermark", "Reminds you of being a cheat scum!", Category.CLIENT);
        addSetting(mode);
    }

}
