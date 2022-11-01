package acme.critical.module;

import java.util.List;
import java.util.ArrayList;
import net.minecraft.client.MinecraftClient;
import acme.critical.module.settings.Setting;

public class Mod {
    private int key;
    private String name;
    private String desc;
    private boolean enabled;
    private Category category;

    private List<Setting> settings = new ArrayList<>();

    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public Mod(String name, String desc, Category category) {
        this.name = name;
        this.desc = desc;
        this.category = category;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public void addSettings(Setting... settings) {
        for (Setting setting : settings) addSetting(setting);
    }

    public void toggle() {
        this.enabled = !this.enabled;

        if (enabled) onEnable();
        else onDisable();
    }

    public void onTick() {

    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) onEnable();
        else onDisable();
    }

    public Category getCategory() {
        return category;
    }

    public enum Category {
        COMBAT("Combat"),       //Combat
        MOVEMENT("Movement"),   //Movement
        VISUAL("Visual"),       //Visual
        MISC("Misc"),           //Misc
        CLIENT("Client");       //HUD/Client

        public String name;

        private Category(String name) {
            this.name = name;
        }
    }
}
