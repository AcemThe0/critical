package acme.critical.module.misc;

import acme.critical.module.Mod;

public class KillFX extends Mod {
	private static int killstreak = 0;

	public KillFX() {
		super("KillFX", "Play sound effects on kill.", Category.MISC);
		get = this;
	}

	public static int getKillstreak() {
		return killstreak;
	}

	public void onTick() {
		if (mc.options.sneakKey.isPressed()) {
			killstreak++;
		}
		super.onTick();
	}

	public static KillFX get;
}
