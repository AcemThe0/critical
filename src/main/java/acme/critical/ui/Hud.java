package acme.critical.ui;

import java.awt.Color;
import java.lang.System;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.module.client.Clickgui;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.Render2DUtils;

public class Hud {
    private static MinecraftClient mc = MinecraftClient.getInstance();
    static int fHeight = mc.textRenderer.fontHeight;

    public static void render(DrawContext context, float tickDelta) {
        if (Clickgui.doLogo.isEnabled()) {
            Render2DUtils.text(
                context, "Critical (JW-" + Critical.INSTANCE.getVersion() + ")",
                1, 1
            );
            Render2DUtils.drawBanana(context, 32, 12, 1);
        }
        renderArrayList(context);
    }

    public static void renderArrayList(DrawContext context) {
        int index = 0;
        int scaledWidth = mc.getWindow().getScaledWidth();

        if (Clickgui.arraylist.getMode() != "Hidden") {

            // There is probably a way to make this neater
            for (Mod module : ModMan.INSTANCE.getEnabledModules()) {
                int modWidth = mc.textRenderer.getWidth(module.getName());
                context.fill(
                    scaledWidth - 3, 1 + (index * fHeight), scaledWidth - 1,
                    fHeight + 2 + (index * fHeight),
                    ColorUtils.arraylistColor(
                        Clickgui.arraylist.getMode(), index,
                        module.getCategory().name
                    )
                );
                context.fill(
                    scaledWidth - 5 - modWidth, 1 + (index * fHeight),
                    scaledWidth - 3, fHeight + 2 + (index * fHeight),
                    new Color(0, 0, 0, 160).getRGB()
                );
                Render2DUtils.text(
                    context, module.getName(), scaledWidth - 4 - modWidth,
                    2 + (index * fHeight)
                );
                index++;
            }
        }
    }
}
