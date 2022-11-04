package acme.critical.utils;
import java.awt.Color;

public class ColorUtils {

    public static int Rainbow(int index, int speed, float saturation) {
        return Color.HSBtoRGB(((System.currentTimeMillis()+index*100) % (speed*1000)) / 4000f, saturation, 1);
    }

}
