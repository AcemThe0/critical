package acme.critical.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import acme.critical.mixin.MinecraftClientAccessor;

public class MiscUtils {
	private static MinecraftClient mc = MinecraftClient.getInstance();

	public static void attack(boolean pressed) {
		mc.options.attackKey.setPressed(pressed);
		if (pressed) ((MinecraftClientAccessor) (Object) mc).doAttack();	
	}

	public static void use(boolean pressed) {
		if (!pressed) mc.options.useKey.timesPressed = 0;
		mc.options.useKey.setPressed(pressed);
	}
}
