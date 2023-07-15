package acme.critical.module.settings;

import acme.critical.module.settings.Setting;

public class TriColorSetting extends Setting {
	public ColorSetting color1 = new ColorSetting("color1", 0);
	public ColorSetting color2 = new ColorSetting("color2", 0);
	public ColorSetting color3 = new ColorSetting("color3", 0);

	public TriColorSetting(String name) {
		super(name);
	}
}
