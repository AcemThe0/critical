package acme.critical.ui.screens.clickgui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.util.math.MatrixStack;

import acme.critical.module.Mod;
import acme.critical.module.settings.*;
import acme.critical.ui.screens.clickgui.ModuleButton;
import acme.critical.ui.screens.clickgui.setting.*;

public class SettingsWindow extends Window {
    public Mod pmod = null;
    public ModuleButton dumbass;
    public List<Component> components = new ArrayList<>();

    public SettingsWindow(int x, int y, int width, int height) {
        super("Settings", x, y, width, height);
        dumbass = new ModuleButton(null, this, height);
    }

    public void
    render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        if (!extended)
            return;

        if ((pmod != ClickGUI.INSTANCE.SelectedMod) &&
            ClickGUI.INSTANCE.SelectedMod != null)
            updateComponents();
        pmod = ClickGUI.INSTANCE.SelectedMod;

        for (Component component : components) {
            component.render(matrices, mouseX, mouseY, delta);
        }
    }

    public void updateComponents() {
        Mod mod = ClickGUI.INSTANCE.SelectedMod;
        components.clear();

        dumbass = new ModuleButton(mod, this, height);

        int setOffset = 0;
        components.add(
            new LabelBox(new Setting(mod.getName()), dumbass, setOffset)
        );
        setOffset += height;
        for (Setting setting : mod.getSettings()) {
            if (setting.isLabeled()) {
                components.add(new LabelBox(setting, dumbass, setOffset));
                setOffset += height;
            }

            if (setting instanceof BooleanSetting)
                components.add(new CheckBox(setting, dumbass, setOffset));
            else if (setting instanceof ColorSetting)
                components.add(new ColorPicker(setting, dumbass, setOffset));
            else if (setting instanceof LabelSetting)
                components.add(new LabelBox(setting, dumbass, setOffset));
            else if (setting instanceof ModeSetting)
                components.add(new ModeBox(setting, dumbass, setOffset));
            else if (setting instanceof NumberSetting)
                components.add(new Slider(setting, dumbass, setOffset));
            else if (setting instanceof KeybindSetting)
                components.add(new Keybind(setting, dumbass, setOffset));
            else if (setting instanceof StringSetting)
                components.add(new TextBox(setting, dumbass, setOffset));
            setOffset += height;
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (!extended)
            return;
        for (Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
        if (!extended)
            return;
        for (Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public void keyPressed(int key) {
        super.keyPressed(key);
        for (Component component : components) {
            component.keyPressed(key);
        }
    }
}
