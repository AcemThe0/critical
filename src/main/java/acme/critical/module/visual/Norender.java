package acme.critical.module.visual;

import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.ColorSetting;
import acme.critical.module.settings.KeybindSetting;

public class Norender extends Mod {
    public BooleanSetting nausea = new BooleanSetting("Nausea", false);
    public BooleanSetting portals = new BooleanSetting("PortalGUI", true);
    public BooleanSetting pumpkin = new BooleanSetting("Pumpkin", true);
    public BooleanSetting fog = new BooleanSetting("Fog", true);
    public BooleanSetting grass = new BooleanSetting("Grass", false);
    public ColorSetting grassColor = new ColorSetting("GrassColor", 0xff4ebe6e);
    public BooleanSetting foliage = new BooleanSetting("Foliage", false);
    public ColorSetting foliageColor =
        new ColorSetting("FoliageColor", 0xffaefe5e);
    public BooleanSetting water = new BooleanSetting("Water", false);
    public ColorSetting waterColor = new ColorSetting("WaterColor", 0xff5eaefe);

    public Norender() {
        super(
            "Norender",
            "Disable or modify rendering of certain effects. F3 + A if some don't work.",
            Category.VISUAL
        );
        addSettings(
            fog, nausea, portals, pumpkin, grass, grassColor, foliage,
            foliageColor, water, waterColor, new KeybindSetting("Key", 0)
        );
    }
}
