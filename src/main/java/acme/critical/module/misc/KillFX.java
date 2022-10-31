package acme.critical.module.misc;

import net.minecraft.entity.LivingEntity;

import acme.critical.module.Mod;
import acme.critical.module.settings.NumberSetting;

public class KillFX extends Mod {
	public NumberSetting cooldown = new NumberSetting("Cooldown", 1, 10, 5, 0.25);

	private static int killstreak = 0;
	private static int killstreakTimer = 0;
	private static boolean killFXEnabled = false;

	public KillFX() {
		super("KillFX", "Play sound effects on kill.", Category.MISC);
		addSetting(cooldown);
	}

	public static int getKillstreak() {
		return killstreak;
	}
	public static int getKillstreakTimer() {
		return killstreakTimer;
	}

	public void onTick() {
		killstreakTimer++;
		if (killstreakTimer > cooldown.getValueInt()*20) {
			killstreak = 0;
			killstreakTimer = 0;
		}

		super.onTick();
	}

	@Override
	public void onEnable() {
		killFXEnabled = true;
	}

	@Override
	public void onDisable() {
		killFXEnabled = false;
		killstreak = 0;
		killstreakTimer = 0;
	}

	// called in PlayerEntityMixin
	public static void onKill() {
		if (!killFXEnabled)
			return;
		killstreak++;
		killstreakTimer = 0;
	}
}
