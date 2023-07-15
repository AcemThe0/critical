package acme.critical.module.movement;

import net.minecraft.client.MinecraftClient;

import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;

public class Step extends Mod {
	public static NumberSetting height = new NumberSetting("Height", 0, 10, 1, 0.1);
	// Safety prevents falling off ledges while sneaking
	public static BooleanSetting safemode = new BooleanSetting("Safety", true);

	private MinecraftClient mc = MinecraftClient.getInstance();

	public Step() {
		super("Step", "Become a horse.", Category.MOVEMENT);
		addSettings(height, safemode, new KeybindSetting("Key", 0));
	}

	@Override
	public void onTick() {
		if (!safemode.isEnabled() | !mc.player.isSneaking()) {
			mc.player.setStepHeight(height.getValueFloat());
		} else {
			mc.player.setStepHeight(0.5f);
		}
	}

	@Override
	public void onDisable() {
		mc.player.setStepHeight(0.5f);
	}
}
