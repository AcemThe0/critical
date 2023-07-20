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
    private static boolean flatmode = false;

    public static int[] getColors(int color) {
        try {
            int[] ret = theme.get(color).clone();
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

    public static boolean isFlat() { return flatmode; }

    public static void updateTheme(List<int[]> newtheme, boolean flat) {
        theme = newtheme;
        flatmode = flat;
    }

    public static void
    coloredRect(DrawContext context, int x, int y, int x2, int y2, int color) {
        if (flatmode) {
            context.fill(x, y, x2, y2, color);
            return;
        }
        context.fill(x, y, x2, y2, new Color(color).brighter().getRGB());
        context.fill(x + 1, y + 1, x2, y2, new Color(color).darker().getRGB());
        context.fill(x + 1, y + 1, x2 - 1, y2 - 1, color);
    }

    public static void
    rect(DrawContext context, int x, int y, int x2, int y2, int color) {
        int[] colors = getColors(color);
        if (flatmode) {
            context.fill(x, y, x2, y2, colors[1]);
            return;
        }
        context.fill(x, y, x2, y2, colors[2]);
        context.fill(x + 1, y + 1, x2, y2, colors[0]);
        context.fill(x + 1, y + 1, x2 - 1, y2 - 1, colors[1]);
    }

    public static void
    inset(DrawContext context, int x, int y, int x2, int y2, int color) {
        int[] colors = getColors(color);
        if (flatmode) {
            context.fill(x, y, x2, y2, colors[0]);
            return;
        }
        context.fill(x, y, x2, y2, 0xff000000);
        context.fill(x + 1, y + 1, x2, y2, colors[2]);
        context.fill(x + 1, y + 1, x2 - 1, y2 - 1, colors[0]);
    }

    public static void
    outset(DrawContext context, int x, int y, int x2, int y2, int color) {
        if (flatmode) {
            int[] colors = getColors(color);
            context.fill(x, y, x2, y2, colors[2]);
            return;
        }
        rect(context, x, y, x2, y2, color);
    }

    public static void
    checkBox(DrawContext context, boolean enabled, int x, int y, int w, int h) {
        if (flatmode) {
            inset(context, x, y, x + w, y + h, 0);
            if (enabled)
                rect(context, x + 2, y + 2, x + w - 2, y + h - 2, 2);
        } else {
            if (enabled)
                inset(context, x, y, x + w, y + h, 0);
            else
                rect(context, x, y, x + w, y + h, 0);
        }
    }

    public static void slider(
        DrawContext context, double val, double min, double max, int x, int y,
        int w, int h
    ) {
        int scrollx = (int)(x + 2 + (((w - 4) * (val - min)) / (max - min)));
        rect(context, x, y, x + w, y + h, 0);
        if (flatmode) {
            rect(context, x, y, scrollx + 2, y + h, 2);
        } else {
            inset(context, x, y + h / 2 - 2, x + w, y + h / 2 + 2, 0);
            rect(context, scrollx - 2, y, scrollx + 2, y + h, 0);
        }
    }

    public static void
    text(DrawContext context, String text, int x, int y, int color) {
        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer, text, x, y, color
        );
    }

    public static void text(DrawContext context, String text, int x, int y) {
        text(context, text, x, y, -1);
    }

    // Horse gonads
    public static void
    drawBanana(DrawContext context, int x, int y, int scale) {
        // Light yellow segments
        context.fill(
            x, y - (1) * scale, x + (3) * scale, y - (2) * scale, 0xffdad11d
        );
        context.fill(
            x + (3) * scale, y - (1) * scale, x + (5) * scale, y - (3) * scale,
            0xffdad11d
        );
        context.fill(
            x + (5) * scale, y - (2) * scale, x + (6) * scale, y - (4) * scale,
            0xffdad11d
        );

        // Dark yellow segments
        context.fill(
            x + (1) * scale, y, x + (5) * scale, y - (1) * scale, 0xffdaa61d
        );
        context.fill(
            x + (5) * scale, y - (1) * scale, x + (6) * scale, y - (2) * scale,
            0xffdaa61d
        );
        context.fill(
            x + (6) * scale, y - (2) * scale, x + (7) * scale, y - (3) * scale,
            0xffdaa61d
        );

        // Dark and light green segments;
        context.fill(
            x + (6) * scale, y - (3) * scale, x + (7) * scale, y - (4) * scale,
            0xff58a518
        );
        context.fill(
            x + (6) * scale, y - (4) * scale, x + (7) * scale, y - (5) * scale,
            0xff5fb31a
        );

        // 0xdad11d bright yellow
        // 0xdaa61d dark yellow
        // 0x5fb31a light green
        // 0x58a518 dark green
    }
}
