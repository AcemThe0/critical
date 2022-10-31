package acme.critical.module.client;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;

public class Arraylist extends Mod {
    public ModeSetting mode = new ModeSetting("Mode", "Rainbow", "Rainbow", "Hidden");

    public Arraylist() {
        super("ModuleList", "Customization!", Category.CLIENT);
        addSetting(mode);
    }
    
    @Override
    public void onEnable() {
        setEnabled(false);
    }

}
