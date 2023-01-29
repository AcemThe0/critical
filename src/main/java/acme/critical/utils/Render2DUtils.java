package acme.critical.utils;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;


public class Render2DUtils {
	public static int[] getcolors(int color) {
		// dark, base, light
		int ret[] = {0, 0, 0};
		switch (color) {
			case 0:
				ret[0] = 0xff7d7d7d;
				ret[1] = 0xffb4b4b4;
				ret[2] = 0xffe0e0e0;
				break;
			case 1:
				ret[0] = 0xff1c2e7c;
				ret[1] = 0xff2e4ac5;
				ret[2] = 0xff5c77ec;
				break;
			default:
				ret[0] = 0xffff00ff;
				ret[1] = ret[0];
				ret[2] = ret[1];
		}
		return ret;
	}

	public static void rect(MatrixStack matrices, int x, int y, int w, int h, int color) {
		//int x2 = x + w;
		//int y2 = y + h;
		int[] colors = getcolors(color);
		DrawableHelper.fill(matrices, x, y, w, h, colors[2]);
		DrawableHelper.fill(matrices, x + 1, y + 1, w, h, colors[0]);
		DrawableHelper.fill(matrices, x + 1, y + 1, w - 1, h - 1, colors[1]);
	}

	public static void inset(MatrixStack matrices, int x, int y, int w, int h, int color) {
		//int x2 = x + w;
		//int y2 = y + h;
		int[] colors = getcolors(color);
		DrawableHelper.fill(matrices, x, y, w, h, 0xff000000 /*colors[1]*/);
		DrawableHelper.fill(matrices, x + 1, y + 1, w, h, colors[2]);
		DrawableHelper.fill(matrices, x + 1, y + 1, w - 1, h - 1, colors[0]);
	}
}
