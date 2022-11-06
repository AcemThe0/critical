package acme.critical.module.client;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;

public class Arraylist extends Mod {
    public ModeSetting mode = new ModeSetting("Mode", "Rainbow", "Rainbow", "Static", "Hidden");

    public Arraylist() {
        super("ModuleList", "Customization!", Category.CLIENT);
        addSetting(mode);
        //removeSetting(key);
    }

    @Override
    public void onEnable() {
        setEnabled(false);
    }
}
