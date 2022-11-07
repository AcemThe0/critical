package acme.critical.module.client;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.BooleanSetting;

public class Clickgui extends Mod {
    public static NumberSetting red = new NumberSetting("Red", 0, 255, 0, 1);
    public static NumberSetting green = new NumberSetting("Green", 0, 255, 100, 1);
    public static NumberSetting blue = new NumberSetting("Blue", 0, 255, 255, 1);
    public static NumberSetting alpha = new NumberSetting("Alpha", 0, 255, 160, 1);
    public static ModeSetting arraylist = new ModeSetting("List", "Rainbow", "Rainbow", "Static", "Category", "Hidden");
    public static BooleanSetting descriptions = new BooleanSetting("Descriptions", true);
    public static BooleanSetting arrows = new BooleanSetting("Arrowkeys", true);

    public Clickgui() {
        super("ClickGUI", "Customization!", Category.CLIENT);
        addSettings(red, green, blue, alpha, arraylist, descriptions, arrows);
    }

    @Override
    public void onEnable() {
        setEnabled(false);
    }
}
