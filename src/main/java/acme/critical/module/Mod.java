package acme.critical.module;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import acme.critical.Critical;
import acme.critical.module.client.Clickgui;
import acme.critical.module.settings.Setting;
import acme.critical.utils.ChatUtils;

public class Mod {
    private int key;
    private String name;
    private String desc;
    private boolean enabled;
    private Category category;
    private boolean subscribed;

    private List<Setting> settings = new ArrayList<>();

    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public Mod(String name, String desc, Category category) {
        this.name = name;
        this.desc = desc;
        this.category = category;
    }

    public List<Setting> getSettings() { return settings; }

    public void addSetting(Setting setting) { settings.add(setting); }

    public void addSettings(Setting... settings) {
        for (Setting setting : settings)
            addSetting(setting);
    }

    public void toggle() {
        if (mc.player != null && mc.world != null) {
            this.enabled = !this.enabled;
            if (enabled)
                onEnable();
            else
                onDisable();

            if (Clickgui.chatFeedback.isEnabled()) {
                String suffix = enabled ? " \u00a7aOn" : " \u00a7cOff";
                ChatUtils.message(name + suffix);
            }
        }
    }

    public void onTick() {}

    public void onRender2D(DrawContext context, float tickDelta) {}

    public void onEnable() {}

    public void onDisable() {}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDesc() { return desc; }

    public void setDesc(String desc) { this.desc = desc; }

    public int getKey() { return key; }

    public void setKey(int key) { this.key = key; }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled)
            onEnable();
        else
            onDisable();
    }

    public Category getCategory() { return category; }

    public enum Category {
        COMBAT("Combat"),     // Combat
        MOVEMENT("Movement"), // Movement
        VISUAL("Visual"),     // Visual
        MISC("Misc"),         // Misc
        CLIENT("Client");     // HUD/GUI

        public String name;

        private Category(String name) { this.name = name; }
    }
}
