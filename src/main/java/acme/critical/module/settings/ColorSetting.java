package acme.critical.module.settings;

import acme.critical.module.settings.Setting;

public class ColorSetting extends Setting {
    private int color;
    // tells color picker to update
    public boolean updated;

    public ColorSetting(String name, int defaultColor) {
        super(name);
        color = defaultColor;
        updated = true;
    }

    public int getColor() { return color; }

    public void setColor(int color) {
        updated = true;
        this.color = color;
    }

    public void setColorCGUI(int color) { this.color = color; }
}
