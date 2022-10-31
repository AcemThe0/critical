package acme.critical.ui;

import java.awt.Color;
import java.util.List;
import java.lang.System;
import java.util.Comparator;

import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.utils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import acme.critical.module.client.Arraylist;
import net.minecraft.client.util.math.MatrixStack;

public class Hud {
    private static MinecraftClient mc = MinecraftClient.getInstance();
    //static Arraylist arraylist = new Arraylist();

    public static void render(MatrixStack matrices, float tickDelta) {
        renderArrayList(matrices);
    }

    public static void renderArrayList(MatrixStack matrices) {
        if (ModMan.INSTANCE.getModules().get(ModMan.INSTANCE.getWatermarkpos()).isEnabled()) {
            String WatermarkText = Critical.Hack;
            if (ModMan.INSTANCE.getModules().get(ModMan.INSTANCE.getWatermarkpos()).getSettings().get(0).getIndex() == 1) {
                WatermarkText += " v" + Critical.Version;
            }
            mc.textRenderer.drawWithShadow(matrices, WatermarkText, 0, 0, -1);
        }

        if (ModMan.INSTANCE.getModules().get(ModMan.INSTANCE.getArraylistpos()).isEnabled()) {
            int index = 0;
            int scaledWidth = mc.getWindow().getScaledWidth();
            //int scaledHeight = mc.getWindow().getScaledHeight();

            if(ModMan.INSTANCE.getModules().get(ModMan.INSTANCE.getArraylistpos()).getSettings().get(0).getIndex() == 0) {
                for (Mod module : ModMan.INSTANCE.getEnabledModules()) {
                    mc.textRenderer.drawWithShadow(matrices, module.getName(), scaledWidth - 4 - mc.textRenderer.getWidth(module.getName()), 5 + (index * mc.textRenderer.fontHeight), ColorUtils.Rainbow(index, 4, 0.7f));
                    index++;
                }
            } else {
                for (Mod module : ModMan.INSTANCE.getEnabledModules()) {
                    mc.textRenderer.drawWithShadow(matrices, module.getName(), scaledWidth - 4 - mc.textRenderer.getWidth(module.getName()), 5 + (index * mc.textRenderer.fontHeight), Color.white.getRGB());
                    index++;
                }
            }
        }
    }
}