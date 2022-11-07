package acme.critical.utils;

import java.awt.Color;
import java.util.HashMap;
import acme.critical.module.client.Clickgui;

public class ColorUtils {

    public static int contrast() {
        return new Color(Clickgui.red.getValueInt(), Clickgui.green.getValueInt(), Clickgui.blue.getValueInt(), Clickgui.alpha.getValueInt()).getRGB();
    }

    public static int Rainbow(int index, int speed, float saturation) {
        return Color.HSBtoRGB(((System.currentTimeMillis()+index*100) % (speed*1000)) / 4000f, saturation, 1);
    }

    public static int getColorByCategory(String category) {
        int alpha = Clickgui.alpha.getValueInt();
        HashMap<String, Integer> colors = new HashMap<String, Integer>();
        colors.put("Combat", new Color(220, 0, 0, alpha).getRGB());
        colors.put("Movement", new Color(0, 220, 0, alpha).getRGB());
        colors.put("Visual", new Color(255, 160, 0, alpha).getRGB());
        colors.put("Misc", new Color(0, 0, 200, alpha).getRGB());
        colors.put("Client", new Color(64, 64, 64, alpha).getRGB());

        return colors.get(category) != null ? colors.get(category) : new Color(0, 0, 0, alpha).getRGB();
    }

    public static int arraylistColor(String mode, int rainbowIndex, String category) {
        HashMap<String, Integer> colors = new HashMap<String, Integer>();
        colors.put("Rainbow", Rainbow(rainbowIndex, 4, 0.7f));
        colors.put("Static", contrast());
        colors.put("Category", getColorByCategory(category));

        return colors.get(mode) != null ? colors.get(mode) : new Color(0, 0, 0, Clickgui.alpha.getValueInt()).getRGB();
    }

}
