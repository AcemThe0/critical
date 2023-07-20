package acme.critical.module.settings;

public class Setting {
    private String name;
    private boolean visible = true;
    private boolean labeled = false;

    public Setting(String name) { this.name = name; }

    public boolean isVisible() { return visible; }

    public void setVisible() { this.visible = visible; }

    public boolean isLabeled() { return labeled; }

    public void setLabeled(boolean labeled) { this.labeled = labeled; }

    public String getName() { return name; }
}
