package acme.critical.utils;

import java.awt.Color;
import java.lang.IndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

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
    coloredRect(MatrixStack matrices, int x, int y, int x2, int y2, int color) {
        if (flatmode) {
            DrawableHelper.fill(matrices, x, y, x2, y2, color);
            return;
        }
        DrawableHelper.fill(matrices, x, y, x2, y2, new Color(color).brighter().getRGB());
        DrawableHelper.fill(matrices, x + 1, y + 1, x2, y2, new Color(color).darker().getRGB());
        DrawableHelper.fill(matrices, x + 1, y + 1, x2 - 1, y2 - 1, color);
    }

    public static void
    rect(MatrixStack matrices, int x, int y, int x2, int y2, int color) {
        int[] colors = getColors(color);
        if (flatmode) {
            DrawableHelper.fill(matrices, x, y, x2, y2, colors[1]);
            return;
        }
        DrawableHelper.fill(matrices, x, y, x2, y2, colors[2]);
        DrawableHelper.fill(matrices, x + 1, y + 1, x2, y2, colors[0]);
        DrawableHelper.fill(matrices, x + 1, y + 1, x2 - 1, y2 - 1, colors[1]);
    }

    public static void
    inset(MatrixStack matrices, int x, int y, int x2, int y2, int color) {
        int[] colors = getColors(color);
        if (flatmode) {
            DrawableHelper.fill(matrices, x, y, x2, y2, colors[0]);
            return;
        }
        DrawableHelper.fill(matrices, x, y, x2, y2, 0xff000000);
        DrawableHelper.fill(matrices, x + 1, y + 1, x2, y2, colors[2]);
        DrawableHelper.fill(matrices, x + 1, y + 1, x2 - 1, y2 - 1, colors[0]);
    }

    public static void
    checkBox(MatrixStack matrices, boolean enabled, int x, int y, int w, int h) {
        if (flatmode) {
            inset(matrices, x, y, x + w, y + h, 0);
            if (enabled)
                rect(matrices, x + 2, y + 2, x + w - 2, y + h - 2, 2);
        } else {
            if (enabled) {
                inset(matrices, x, y, x + w, y + h, 0);
                DrawableHelper.fill(matrices, x + 4, y + 4, x + w - 4, y + h - 4, 0xff000000);
            } else {
                inset(matrices, x, y, x + w, y + h, 0);
            }
        }
    }

    public static void slider(
        MatrixStack matrices, double val, double min, double max, int x, int y,
        int w, int h
    ) {
        int scrollx = (int)(x + 2 + (((w - 4) * (val - min)) / (max - min)));
        rect(matrices, x, y, x + w, y + h, 0);
        if (flatmode) {
            rect(matrices, x, y, scrollx + 2, y + h, 2);
        } else {
            inset(matrices, x, y + h / 2 - 2, x + w, y + h / 2 + 2, 0);
            rect(matrices, scrollx - 2, y, scrollx + 2, y + h, 0);
        }
    }

    public static void
    text(MatrixStack matrices, String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(
            matrices, text, x, y, color
        );
    }

    public static void text(MatrixStack matrices, String text, int x, int y) {
        text(matrices, text, x, y, -1);
    }

    // Horse gonads
    public static void
    drawBanana(MatrixStack matrices, int x, int y, int scale) {
        // Light yellow segments
        DrawableHelper.fill(matrices, 
            x, y - (1) * scale, x + (3) * scale, y - (2) * scale, 0xffdad11d
        );
        DrawableHelper.fill(matrices, 
            x + (3) * scale, y - (1) * scale, x + (5) * scale, y - (3) * scale,
            0xffdad11d
        );
        DrawableHelper.fill(matrices, 
            x + (5) * scale, y - (2) * scale, x + (6) * scale, y - (4) * scale,
            0xffdad11d
        );

        // Dark yellow segments
        DrawableHelper.fill(matrices, 
            x + (1) * scale, y, x + (5) * scale, y - (1) * scale, 0xffdaa61d
        );
        DrawableHelper.fill(matrices, 
            x + (5) * scale, y - (1) * scale, x + (6) * scale, y - (2) * scale,
            0xffdaa61d
        );
        DrawableHelper.fill(matrices, 
            x + (6) * scale, y - (2) * scale, x + (7) * scale, y - (3) * scale,
            0xffdaa61d
        );

        // Dark and light green segments;
        DrawableHelper.fill(matrices, 
            x + (6) * scale, y - (3) * scale, x + (7) * scale, y - (4) * scale,
            0xff58a518
        );
        DrawableHelper.fill(matrices, 
            x + (6) * scale, y - (4) * scale, x + (7) * scale, y - (5) * scale,
            0xff5fb31a
        );

        // 0xdad11d bright yellow
        // 0xdaa61d dark yellow
        // 0x5fb31a light green
        // 0x58a518 dark green
    }
}
