package acme.critical.module.client;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.BooleanSetting;

import acme.critical.module.settings.ColorSetting;
import acme.critical.module.settings.LabelSetting;

import acme.critical.profile.Profile;
import acme.critical.profile.files.ThemeFile;
import acme.critical.utils.Render2DUtils;

import java.util.ArrayList;
import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventTick;

public class Clickgui extends Mod {
    public static ModeSetting arraylist = new ModeSetting("List", "Rainbow", "Rainbow", "Static", "Category", "Hidden");
    public static BooleanSetting descriptions = new BooleanSetting("Descriptions", true);
    public static BooleanSetting chatFeedback = new BooleanSetting("ChatToggle", true);
    public static BooleanSetting arrows = new BooleanSetting("Arrowkeys", true);
    public static ColorSetting colorBase = new ColorSetting("Base", 0xffffffff);
    public static ColorSetting colorTitle = new ColorSetting("Title", 0xffffffff);
    public static ColorSetting colorSelected = new ColorSetting("Selected", 0xffffffff);
    public static BooleanSetting reloadTheme = new BooleanSetting("Save Theme", false);

    public Clickgui() {
        super("ClickGUI", "Customization!", Category.CLIENT);
        colorBase.setLabeled(true);
        colorBase.setColor(Render2DUtils.getColors(0)[1]);
        colorTitle.setLabeled(true);
        colorTitle.setColor(Render2DUtils.getColors(1)[1]);
        colorSelected.setLabeled(true);
        colorSelected.setColor(Render2DUtils.getColors(2)[1]);
        addSettings(arraylist, descriptions, chatFeedback, arrows, colorBase, colorTitle, colorSelected, reloadTheme);
        Critical.INSTANCE.eventBus.subscribe(this);
    }

    @CriticalSubscribe
    public void onTickAlways(EventTick event) {
        if (!reloadTheme.isEnabled()) return;
        reloadTheme.setEnabled(false);

	ArrayList<int[]> theme = new ArrayList<>();
	theme.add(Render2DUtils.getComplements(colorBase.getColor()));
	theme.add(Render2DUtils.getComplements(colorTitle.getColor()));
	theme.add(Render2DUtils.getComplements(colorSelected.getColor()));

	Render2DUtils.updateTheme(theme);

        Profile.INSTANCE.save(ThemeFile.class);
        Profile.INSTANCE.load(ThemeFile.class);
    }

    @Override
    public void onEnable() {
        setEnabled(false);
    }
}
