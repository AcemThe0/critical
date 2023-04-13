package acme.critical.ui.screens.clickgui;

import java.util.List;
import java.util.ArrayList;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.module.settings.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack;
import acme.critical.ui.screens.clickgui.setting.*;

public class OptionsWindow extends Window {
    public Mod module = ModMan.selectedModule;
    private ModuleButton dummy;
    public List<Component> components;

    public OptionsWindow(int x, int y, int width, int height) {
        super("Options", x, y, width, height);

        dummy = new ModuleButton(null, this, height);
        this.module = ModMan.selectedModule;
        this.components = new ArrayList<>();
        int setOffset = height;

        if (module == null) return;

        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                components.add(new CheckBox(setting, dummy, setOffset));
            } else if (setting instanceof ModeSetting) {
                components.add(new ModeBox(setting, dummy, setOffset));
            } else if (setting instanceof NumberSetting) {
                components.add(new Slider(setting, dummy, setOffset));
            } else if (setting instanceof KeybindSetting) {
                components.add(new Keybind(setting, dummy, setOffset));
            } else if (setting instanceof StringSetting) {
                components.add(new TextBox(setting, dummy, setOffset));
            }
        setOffset += height;
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        if (extended) {
            for (Component component : components) {
                component.render(matrices, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                extended = !extended;
            }
        }

        for (Component component : components) {
            if (extended) component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
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
