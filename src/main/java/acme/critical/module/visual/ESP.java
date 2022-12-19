package acme.critical.module.visual;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.KeybindSetting;

import acme.critical.utils.ColorUtils;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

import net.minecraft.util.math.Vec3d;

import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vector4f;

public class ESP extends Mod {
	private ModeSetting mode = new ModeSetting("Mode", "Walls", "2D", "Hitbox");

	public ESP() {
		super("ESP", "Extrasensory perception!", Category.VISUAL);
		addSettings(mode, new KeybindSetting("Key", 0));
	}

	public void onRender2D(MatrixStack matrices, float tickDelta) {
	}
}
