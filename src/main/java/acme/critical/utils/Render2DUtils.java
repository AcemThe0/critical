package acme.critical.utils;

import java.awt.Color;
import java.lang.IndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import acme.critical.utils.ColorUtils;

public class Render2DUtils {
	private static List<int[]> theme = new ArrayList<>();

	private static int[] getcolors(int color) {
		try {
			int[] ret = theme.get(color).clone();
			for (int i = 0; i < ret.length; i++)
				if (ret[i] == 0xffff00ff) ret[i] = ColorUtils.Rainbow();
			return ret;
		} catch (IndexOutOfBoundsException e) {
			int[] ret = {0xffff00ff, 0xffff00ff, 0xffff00ff};
			return ret;
		}
	}

	public static void updateTheme(List<int[]> newtheme) {
		theme = newtheme;
	}

	public static void rect(MatrixStack matrices, int x, int y, int x2, int y2, int color) {
		int[] colors = getcolors(color);
		DrawableHelper.fill(matrices, x, y, x2, y2, colors[2]);
		DrawableHelper.fill(matrices, x + 1, y + 1, x2, y2, colors[0]);
		DrawableHelper.fill(matrices, x + 1, y + 1, x2 - 1, y2 - 1, colors[1]);
	}

	public static void inset(MatrixStack matrices, int x, int y, int x2, int y2, int color) {
		int[] colors = getcolors(color);
		DrawableHelper.fill(matrices, x, y, x2, y2, 0xff000000);
		DrawableHelper.fill(matrices, x + 1, y + 1, x2, y2, colors[2]);
		DrawableHelper.fill(matrices, x + 1, y + 1, x2 - 1, y2 - 1, colors[0]);
	}
}
