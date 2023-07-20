package acme.critical.module.visual;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;

public class Nametags extends Mod {
    private NumberSetting size = new NumberSetting("Size", 0, 7, 2.4, 0.1);

    public Nametags() {
        super(
            "Nametags", "View additional info about entities.", Category.VISUAL
        );
        addSettings(size, new KeybindSetting("Key", 0));
    }

    public float getSize() { return size.getValueFloat(); }
}
