package acme.critical.profile.files;

import java.lang.Integer;
import java.util.ArrayList;

import acme.critical.profile.ProfileFile;
import acme.critical.utils.Render2DUtils;

import acme.critical.Critical;

public class ThemeFile extends ProfileFile {
	public ThemeFile() {
		super("theme.csv");
	}

	@Override
	public String onSave() {
		return "//130\n"
			+ "#7d7d7d, #b4b4b4, #e0e0e0\n"
			+ "#1c2e7c, #2e4ac5, #5c77ec\n"
			+ "#586198, #7e88bf, #bec7ff\n";
	}

	@Override
	public void onLoad(String string) {
		ArrayList<int[]> out = new ArrayList<>();

		String[] stringlines = string.split("\n");
		for (String line : stringlines) {
			if (line.charAt(0) == '/' && line.charAt(1) == '/') continue;
			String[] colors = line.split(", ");
			int[] schema = new int[colors.length];
			for (int i = 0; i < colors.length; i++) {
				schema[i] = Integer.decode(colors[i]) | 0xff000000;
			}
			out.add(schema);
		}
		
		Render2DUtils.updateTheme(out);
	}
}
