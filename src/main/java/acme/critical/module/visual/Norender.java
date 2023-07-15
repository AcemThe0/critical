package acme.critical.module.visual;

import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Norender extends Mod {
	// Allow GUI use while in a nether portal
	// ClientPlayerEntityMixin
	public BooleanSetting nausea = new BooleanSetting("Nausea", false);
	public BooleanSetting portals = new BooleanSetting("Portals", true);
	public BooleanSetting pumpkin = new BooleanSetting("Pumpkin", true);

	public Norender() {
		super("Norender", "Disable rendering of certain effects.", Category.VISUAL);
		addSettings(nausea, portals, pumpkin, new KeybindSetting("Key", 0));
	}
}
