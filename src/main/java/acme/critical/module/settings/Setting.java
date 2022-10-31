package acme.critical.module.settings;

public abstract class Setting {
    private String name;
    private boolean visible = true;

    public Setting(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible() {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public abstract int getIndex();
}
