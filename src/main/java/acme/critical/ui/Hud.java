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
    static int fHeight = mc.textRenderer.fontHeight;

    public static void render(MatrixStack matrices, float tickDelta) {
        mc.textRenderer.drawWithShadow(matrices, "Critical (JW-1.2.2)", 1, 1, -1);
        renderArrayList(matrices);
    }

    public static void renderArrayList(MatrixStack matrices) {
        int index = 0;
        int scaledWidth = mc.getWindow().getScaledWidth();
        //int scaledHeight = mc.getWindow().getScaledHeight();

        if (Clickgui.arraylist.getMode() != "Hidden") {

            //There is probably a way to make this neater
            for (Mod module : ModMan.INSTANCE.getEnabledModules()) {
                int modWidth = mc.textRenderer.getWidth(module.getName());
                DrawableHelper.fill(matrices, scaledWidth-3, 1+(index*fHeight), scaledWidth-1, fHeight+2+(index*fHeight), ColorUtils.arraylistColor(Clickgui.arraylist.getMode(), index, module.getCategory().name));
                DrawableHelper.fill(matrices, scaledWidth-5-modWidth, 1+(index*fHeight), scaledWidth-3, fHeight+2+(index*fHeight), new Color(0, 0, 0, 160).getRGB());
                mc.textRenderer.drawWithShadow(matrices, module.getName(), scaledWidth-4-modWidth, 2+(index*fHeight), -1);
                index++;
            }
        }
    }
}
