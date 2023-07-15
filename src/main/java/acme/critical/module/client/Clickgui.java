package acme.critical.module.client;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.BooleanSetting;

import acme.critical.module.settings.ColorSetting;
import acme.critical.module.settings.LabelSetting;

public class Clickgui extends Mod {
    public static ModeSetting arraylist = new ModeSetting("List", "Rainbow", "Rainbow", "Static", "Category", "Hidden");
    public static BooleanSetting descriptions = new BooleanSetting("Descriptions", true);
    public static BooleanSetting chatFeedback = new BooleanSetting("ChatToggle", true);
    public static BooleanSetting arrows = new BooleanSetting("Arrowkeys", true);
    public static LabelSetting testLabel = new LabelSetting("Label");
    public static ColorSetting testColor = new ColorSetting("Color", 0xffffffff);
    public static BooleanSetting reloadTheme = new BooleanSetting("ReloadTheme", false);

    public Clickgui() {
        super("ClickGUI", "Customization!", Category.CLIENT);
        addSettings(arraylist, descriptions, chatFeedback, arrows, testLabel, testColor, reloadTheme);
    }

    @Override
    public void onEnable() {
        setEnabled(false);
    }
}
