package acme.critical.ui;

import java.awt.Color;
import java.lang.System;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.utils.ColorUtils;
import acme.critical.module.client.Clickgui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class Hud {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void render(MatrixStack matrices, float tickDelta) {
        mc.textRenderer.drawWithShadow(matrices, "Critical (JW-1.2.0)", 1, 1, -1);
        renderArrayList(matrices);
    }

    public static void renderArrayList(MatrixStack matrices) {
        int index = 0;
        int scaledWidth = mc.getWindow().getScaledWidth();
        //int scaledHeight = mc.getWindow().getScaledHeight();

        if (Clickgui.arraylist.getMode() != "Hidden") {

            //There is probably a way to make this neater
            for (Mod module : ModMan.INSTANCE.getEnabledModules()) {
                DrawableHelper.fill(matrices, scaledWidth-3, 1+(index*mc.textRenderer.fontHeight), scaledWidth-1, mc.textRenderer.fontHeight+2+(index*mc.textRenderer.fontHeight), ColorUtils.arraylistColor(Clickgui.arraylist.getMode(), index, module.getCategory().name));
                DrawableHelper.fill(matrices, scaledWidth-5-mc.textRenderer.getWidth(module.getName()), 1+(index*mc.textRenderer.fontHeight), scaledWidth-3, mc.textRenderer.fontHeight+2+(index*mc.textRenderer.fontHeight), new Color(0, 0, 0, 160).getRGB());
                mc.textRenderer.drawWithShadow(matrices, module.getName(), scaledWidth-4-mc.textRenderer.getWidth(module.getName()), 2+(index*mc.textRenderer.fontHeight), -1);
                index++;
            }
        }
    }
}
