package acme.critical.module.client;

import java.util.ArrayList;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventTick;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.ColorSetting;
import acme.critical.module.settings.LabelSetting;
import acme.critical.module.settings.ModeSetting;
import acme.critical.profile.Profile;
import acme.critical.profile.files.ThemeFile;
import acme.critical.utils.Render2DUtils;

public class Clickgui extends Mod {
    public static ModeSetting arraylist = new ModeSetting(
        "List", "Rainbow", "Rainbow", "Static", "Category", "Hidden"
    );
    public static BooleanSetting descriptions =
        new BooleanSetting("Descriptions", true);
    public static BooleanSetting chatFeedback =
        new BooleanSetting("ChatToggle", true);
    public static BooleanSetting arrows = new BooleanSetting("Arrowkeys", true);
    public static ColorSetting colorBase = new ColorSetting("Base", 0xffffffff);
    public static ColorSetting colorTitle =
        new ColorSetting("Title", 0xffffffff);
    public static ColorSetting colorSelected =
        new ColorSetting("Selected", 0xffffffff);
    public static BooleanSetting flatMode =
        new BooleanSetting("FlatMode", false);
    public static BooleanSetting saveTheme =
        new BooleanSetting("SaveTheme", false);
    public static BooleanSetting loadTheme =
        new BooleanSetting("LoadTheme", false);

    public Clickgui() {
        super("ClickGUI", "Customization!", Category.CLIENT);
        colorBase.setLabeled(true);
        colorTitle.setLabeled(true);
        colorSelected.setLabeled(true);
        updateColors();
        addSettings(
            arraylist, descriptions, chatFeedback, arrows, colorBase,
            colorTitle, colorSelected, flatMode, saveTheme, loadTheme
        );
        Critical.INSTANCE.eventBus.subscribe(this);
    }

    @CriticalSubscribe
    public void onTickAlways(EventTick event) {
        if (loadTheme.isEnabled()) {
            loadTheme.setEnabled(false);
            Profile.INSTANCE.load(ThemeFile.class);
            updateColors();
        }

        if (!saveTheme.isEnabled())
            return;
        saveTheme.setEnabled(false);

        ArrayList<int[]> theme = new ArrayList<>();
        theme.add(Render2DUtils.getComplements(colorBase.getColor()));
        theme.add(Render2DUtils.getComplements(colorTitle.getColor()));
        theme.add(Render2DUtils.getComplements(colorSelected.getColor()));

        Render2DUtils.updateTheme(theme, flatMode.isEnabled());

        Profile.INSTANCE.save(ThemeFile.class);
    }

    @Override
    public void onEnable() {
        setEnabled(false);
    }

    private void updateColors() {
        colorBase.setColor(Render2DUtils.getColors(0)[1]);
        colorTitle.setColor(Render2DUtils.getColors(1)[1]);
        colorSelected.setColor(Render2DUtils.getColors(2)[1]);
        flatMode.setEnabled(Render2DUtils.isFlat());
    }
}
