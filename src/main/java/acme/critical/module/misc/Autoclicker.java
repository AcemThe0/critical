package acme.critical.module.misc;

import net.minecraft.client.MinecraftClient;

import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;

public class Autoclicker extends Mod {
	private BooleanSetting lClick = new BooleanSetting("LClick", false);
	private BooleanSetting rClick = new BooleanSetting("RClick", false);
	private NumberSetting lDelay = new NumberSetting("LDelay", 0, 60, 1, 1);
	private NumberSetting rDelay = new NumberSetting("RDelay", 0, 60, 1, 1);

	private short lClickTimer = 0;
	private short rClickTimer = 0;

	private MinecraftClient mc = MinecraftClient.getInstance();

	public Autoclicker() {
		super("Autoclicker", "Automatically click mouse buttons.", Category.MISC);
		addSettings(lClick, lDelay, rClick, rDelay, new KeybindSetting("Key", 0));
	}

	@Override
	public void onTick() {
		if (lClick.isEnabled()) {
			lClickTimer += 1;
			if (lClickTimer > lDelay.getValueInt()) {
				/*mc.options.attackKey.setPressed(true);
				mc.options.attackKey.setPressed(false);*/
				lClickTimer = 0;
			}
		}
		if (rClick.isEnabled()) {
			rClickTimer += 1;
			if (rClickTimer > rDelay.getValueInt()) {
				/*mc.options.useKey.setPressed(true);
				mc.options.useKey.setPressed(false);*/
				rClickTimer = 0;
			}
		}
	}

	@Override
	public void onEnable() {
		lClickTimer = 0;
		rClickTimer = 0;
		mc.options.attackKey.setPressed(false);
		mc.options.useKey.setPressed(false);
	}

	@Override
	public void onDisable() {
		lClickTimer = 0;
		rClickTimer = 0;
		mc.options.attackKey.setPressed(false);
		mc.options.attackKey.setPressed(false);
	}
}
