package acme.critical.utils;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {
    public static double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double getDistanceSquared(double x1, double z1, double x2, double z2) {
        double deltaX = x2 - x1;
        double deltaZ = z2 - z1;
        double val = (deltaX * deltaX) + (deltaZ * deltaZ);
        return Math.abs(val) > 0.00001 ? val : 0;
    }
}
