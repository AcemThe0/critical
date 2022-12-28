package acme.critical.module.visual;

import acme.critical.module.Mod;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;

public class Nametags extends Mod {
	private NumberSetting size = new NumberSetting("Size", 0.0f, 1.0f, 0.3f, 0.1f);

	public Nametags() {
		super("Nametags", "View additional info about entities.", Category.VISUAL);
		addSettings(size, new KeybindSetting("Key", 0));
	}

	public float getSize() {
		return size.getValueFloat();
	}
}
