package acme.critical.module.misc;

import net.minecraft.client.MinecraftClient;

import acme.critical.mixinterface.KeyBindingInterface;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;

public class Autoclicker extends Mod {
	private BooleanSetting rClick = new BooleanSetting("RClick", true);
	private NumberSetting rDelay = new NumberSetting("RDelay", 1, 60, 1, 1);

	private short rClickTimer = 0;

	private MinecraftClient mc = MinecraftClient.getInstance();

	public Autoclicker() {
		super("Autoclicker", "Automatically click mouse buttons.", Category.MISC);
		addSettings(rClick, rDelay, new KeybindSetting("Key", 0));
	}

	@Override
	public void onTick() {
		if (rClick.isEnabled()) {
			rClickTimer += 1;
			if (rClickTimer == rDelay.getValueInt()) {
				mc.options.useKey.setPressed(true);
			}
			if (rClickTimer > rDelay.getValueInt()) {
				((KeyBindingInterface) (Object) mc.options.useKey).release();
				rClickTimer = 0;
			}
		}
	}

	@Override
	public void onEnable() {
		rClickTimer = 0;
		((KeyBindingInterface) (Object) mc.options.useKey).release();
	}

	@Override
	public void onDisable() {
		rClickTimer = 0;
		((KeyBindingInterface) (Object) mc.options.useKey).release();
	}
}
