package acme.critical.profile.files;

import java.lang.IndexOutOfBoundsException;
import java.lang.Integer;
import java.util.ArrayList;

import acme.critical.Critical;
import acme.critical.profile.ProfileFile;
import acme.critical.utils.Render2DUtils;

public class ThemeFile extends ProfileFile {
    public ThemeFile() { super("theme.csv"); }

    @Override
    public String onSave() {
        int[] c1 = Render2DUtils.getColors(0);
        int[] c2 = Render2DUtils.getColors(1);
        int[] c3 = Render2DUtils.getColors(2);
        for (int i = 0; i < 3; i++) {
            c1[i] &= 0xffffff;
            c2[i] &= 0xffffff;
            c3[i] &= 0xffffff;
        }
        return "//140" + (Render2DUtils.isFlat() ? "b" : "a") + "\n"
            + "// base\n" +
            String.format("#%06X, #%06X, #%06X\n", c1[0], c1[1], c1[2]) +
            "// titles\n" +
            String.format("#%06X, #%06X, #%06X\n", c2[0], c2[1], c2[2]) +
            "// selected\n" +
            String.format("#%06X, #%06X, #%06X\n", c3[0], c3[1], c3[2]);
    }

    @Override
    public String onDefault() {
        return "//140a\n"
            + "// base\n"
            + "#7B7B7B, #B0B0B0, #FBFBFB\n"
            + "// titles\n"
            + "#203389, #2E4AC5, #4169FF\n"
            + "// selected\n"
            + "#585F85, #7E88BF, #B4C2FF\n";
    }

    @Override
    public void onLoad(String string) {
        ArrayList<int[]> out = new ArrayList<>();

        String[] stringlines = string.split("\n");
        for (String line : stringlines) {
            if (line.charAt(0) == '/' && line.charAt(1) == '/')
                continue;
            String[] colors = line.split(", ");
            int[] schema = new int[colors.length];
            for (int i = 0; i < colors.length; i++) {
                schema[i] = Integer.decode(colors[i]) | 0xff000000;
            }
            out.add(schema);
        }

        boolean flat = false;
        try {
            flat = (stringlines[0].charAt(5)) == 'b';
        } catch (IndexOutOfBoundsException ex) {
            // there is nothing we can do. its over
        }

        Render2DUtils.updateTheme(out, flat);
    }
}
