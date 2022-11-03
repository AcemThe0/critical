package acme.critical.ui;

import java.awt.Color;
import java.util.List;
import java.lang.System;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.utils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import acme.critical.module.client.Arraylist;
import net.minecraft.client.util.math.MatrixStack;

import acme.critical.module.misc.KillFX;

public class Hud {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void render(MatrixStack matrices, float tickDelta) {
        mc.textRenderer.drawWithShadow(matrices, "Critical", 0, 0, -1);
	mc.textRenderer.drawWithShadow(
		matrices,
		String.format("Killstreak: %d", KillFX.getKillstreak()),
		0, 32, -1
	);
        renderArrayList(matrices);
    }

    public static void renderArrayList(MatrixStack matrices) {
        int index = 0;
        int scaledWidth  = mc.getWindow().getScaledWidth();
        int scaledHeight = mc.getWindow().getScaledHeight();

        List<Mod> enabled = ModMan.INSTANCE.getEnabledModules();

        /*switch(Arraylist.mode.getMode()) {
        case "Rainbow":*/
            for (Mod module : enabled) {
                mc.textRenderer.drawWithShadow(matrices, module.getName(), scaledWidth-4-mc.textRenderer.getWidth(module.getName()), 5+(index*mc.textRenderer.fontHeight), ColorUtils.Rainbow(index, 4, 0.7f));
                index++;
            }
        /*break;
        case "Static":
            for (Mod module : enabled) {
                mc.textRenderer.drawWithShadow(matrices, module.getName(), scaledWidth-4-mc.textRenderer.getWidth(module.getName()), 5+(index*mc.textRenderer.fontHeight), -1);
                index++;
            }
        break;
        case "Hidden":
        break;
        }*/
    }
}
