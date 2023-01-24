package acme.critical.utils;

import java.awt.Color;
import java.util.HashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import acme.critical.module.client.Clickgui;

public class ColorUtils {
    public static int friendColor = new Color(127, 255, 127).getRGB();

    // Should be removed before merge
    public static int contrast() {
        return (
            Clickgui.alpha.getValueInt() << (8*3))
            | (Clickgui.red.getValueInt() << (8*2))
            | (Clickgui.green.getValueInt() << (8*1))
            | (Clickgui.blue.getValueInt())
        ;
    }

    public static int Rainbow(int index, int speed, float saturation) {
        return Color.HSBtoRGB(((System.currentTimeMillis()+index*100) % (speed*1000)) / 4000f, saturation, 1);
    }

    public static int Rainbow() {
        return Rainbow(0, 4, 0.7f);
    }

    public static int GetEntColor(EntityType type) {
        // boss mobs
        if (
            type == EntityType.ENDER_DRAGON
            || type == EntityType.WITHER
            || type == EntityType.WARDEN
        ) return new Color(240, 150, 255).getRGB();
        return switch (type.getSpawnGroup()) {
            case CREATURE -> new Color(10, 200, 10).getRGB();
            case MONSTER -> new Color(200, 20, 20).getRGB();
            case AMBIENT -> new Color(0, 50, 60).getRGB();
            case WATER_AMBIENT, WATER_CREATURE, UNDERGROUND_WATER_CREATURE, AXOLOTLS -> new Color(0, 30, 100).getRGB();
            default -> new Color(200, 170, 0).getRGB();
        };
    }

    public static int GetEntColor(Entity entity) {
        return GetEntColor(entity.getType());
    }

    public static int getColorByCategory(String category) {
        int alpha = Clickgui.alpha.getValueInt();
        HashMap<String, Integer> colors = new HashMap<String, Integer>();
        colors.put("Combat", new Color(220, 0, 0, alpha).getRGB());
        colors.put("Movement", new Color(0, 220, 0, alpha).getRGB());
        colors.put("Visual", new Color(255, 160, 0, alpha).getRGB());
        colors.put("Misc", new Color(0, 0, 200, alpha).getRGB());
        colors.put("Client", new Color(64, 64, 64, alpha).getRGB());

        return colors.get(category) != null ? (colors.get(category)) : new Color(0, 0, 0, alpha).getRGB();
    }

    public static int arraylistColor(String mode, int rainbowIndex, String category) {
        HashMap<String, Integer> colors = new HashMap<String, Integer>();
        colors.put("Rainbow", Rainbow(rainbowIndex, 4, 0.7f));
        colors.put("Static", contrast());
        colors.put("Category", getColorByCategory(category));

        return colors.get(mode) != null ? colors.get(mode) : new Color(0, 0, 0, Clickgui.alpha.getValueInt()).getRGB();
    }

}
