package acme.critical.module.misc;

import net.minecraft.client.MinecraftClient;

import acme.critical.mixin.MinecraftClientAccessor;
import acme.critical.mixinterface.KeyBindingInterface;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;

public class Autoclicker extends Mod {
	private BooleanSetting lClick = new BooleanSetting("LClick", false);
	private NumberSetting lDelay = new NumberSetting("LDelay", 1, 60, 40, 1);
	private BooleanSetting rClick = new BooleanSetting("RClick", true);
	private NumberSetting rDelay = new NumberSetting("RDelay", 1, 60, 1, 1);

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
			if (lClickTimer == lDelay.getValueInt()) {
				mc.options.attackKey.setPressed(true);
				((MinecraftClientAccessor) (Object) mc).doAttack();
			}
			if (lClickTimer > lDelay.getValueInt()) {
				((KeyBindingInterface) (Object) mc.options.attackKey).release();
				lClickTimer = 0;
			}
		}
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
