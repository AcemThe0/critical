package acme.critical.utils;

import java.awt.Color;
import java.lang.IndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import acme.critical.utils.ColorUtils;

public class Render2DUtils {
	private static List<int[]> theme = new ArrayList<>();

	public static int[] getColors(int color) {
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

	public static int[] getComplements(int color) {
		int[] ret = new int[3];

		ret[0] = new Color(color).darker().getRGB();
		ret[1] = color;
		ret[2] = new Color(color).brighter().getRGB();

		return ret;
	}

	public static void updateTheme(List<int[]> newtheme) {
		theme = newtheme;
	}

	public static void rect(DrawContext context, int x, int y, int x2, int y2, int color) {
		int[] colors = getColors(color);
		context.fill(x, y, x2, y2, colors[2]);
		context.fill(x + 1, y + 1, x2, y2, colors[0]);
		context.fill(x + 1, y + 1, x2 - 1, y2 - 1, colors[1]);
	}

	public static void inset(DrawContext context, int x, int y, int x2, int y2, int color) {
		int[] colors = getColors(color);
		context.fill(x, y, x2, y2, 0xff000000);
		context.fill(x + 1, y + 1, x2, y2, colors[2]);
		context.fill(x + 1, y + 1, x2 - 1, y2 - 1, colors[0]);
	}

	public static void slider(
		DrawContext context, double val, double min, double max,
		int x, int y, int w, int h
	) {
		rect(context, x, y, x + w, y + h, 0);
		inset(context, x, y + h/2 - 2, x + w, y + h/2 + 2, 0);
		int scrollx = (int) (x + 2 + (((w - 4) * (val - min)) / (max - min)));
		rect(context, scrollx - 2, y, scrollx + 2, y + h, 0);
	}

	public static void text(DrawContext context, String text, int x, int y, int color) {
		context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, text, x, y, color);
	}

	public static void text(DrawContext context, String text, int x, int y) {
		text(context, text, x, y, -1);
	}

	//Horse gonads
	public static void drawBanana(DrawContext context, int x, int y, int scale) {
		//Light yellow segments
		context.fill(x, y-(1)*scale, x+(3)*scale, y-(2)*scale, 0xffdad11d);
		context.fill(x+(3)*scale, y-(1)*scale, x+(5)*scale, y-(3)*scale, 0xffdad11d);
		context.fill(x+(5)*scale, y-(2)*scale, x+(6)*scale, y-(4)*scale, 0xffdad11d);

		//Dark yellow segments
		context.fill(x+(1)*scale, y, x+(5)*scale, y-(1)*scale, 0xffdaa61d);
		context.fill(x+(5)*scale, y-(1)*scale, x+(6)*scale, y-(2)*scale, 0xffdaa61d);
		context.fill(x+(6)*scale, y-(2)*scale, x+(7)*scale, y-(3)*scale, 0xffdaa61d);

		//Dark and light green segments;
		context.fill(x+(6)*scale, y-(3)*scale, x+(7)*scale, y-(4)*scale, 0xff58a518);
		context.fill(x+(6)*scale, y-(4)*scale, x+(7)*scale, y-(5)*scale, 0xff5fb31a);

		//0xdad11d bright yellow
		//0xdaa61d dark yellow
		//0x5fb31a light green
		//0x58a518 dark green
	}

}
