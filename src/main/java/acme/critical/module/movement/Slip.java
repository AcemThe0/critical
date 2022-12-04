package acme.critical.module.movement;

import acme.critical.module.Mod;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;

public class Slip extends Mod {
	// TODO: Possibly modify friction based on speed modifiers (crouch/sprint) to circumvent 70 b/s cap
	public static NumberSetting friction = new NumberSetting("Friction", 0.1, 1.1, 0.2, 0.01);

	public Slip() {
		super("Slip", "Adjust the friction of blocks you walk on.", Category.MOVEMENT);
		addSettings(friction, new KeybindSetting("Key", 0));
	}

	public static float getFriction() {
		return friction.getValueFloat();
	}
}
