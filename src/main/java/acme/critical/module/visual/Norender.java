package acme.critical.module.visual;

import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Norender extends Mod {
	// Allow GUI use while in a nether portal
	// ClientPlayerEntityMixin
	private BooleanSetting doPortals = new BooleanSetting("Portals", true);

	public Norender() {
		super("Norender", "Disable rendering of certain effects.", Category.VISUAL);
		addSettings(doPortals, new KeybindSetting("Key", 0));
	}

	public boolean portalsEnabled() {
		return doPortals.isEnabled();
	}
}
