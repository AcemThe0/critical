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
    public Window parent;
    public int offset;
    private ModuleButton baka = new ModuleButton(ModMan.INSTANCE.selectedModule, this, height);
    public List<Component> components;

    public OptionsWindow(int x, int y, int width, int height) {
        super("Options", x, y, width, height);
        this.components = new ArrayList<>();
        this.parent = parent;
        this.offset = offset;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        for (Component component : components) {
            component.render(matrices, mouseX, mouseY, delta);
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        for (Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);


        Mod module = ModMan.INSTANCE.selectedModule;
        components.clear();

        if (module != null) {
            baka.changeModule(ModMan.INSTANCE.selectedModule);
                int setOffset = height-15; //HACK'd
                    for (Setting setting : module.getSettings()) {
                if (setting instanceof BooleanSetting) {
                    components.add(new CheckBox(setting, baka, setOffset));
                } else if (setting instanceof ModeSetting) {
                    components.add(new ModeBox(setting, baka, setOffset));
                } else if (setting instanceof NumberSetting) {
                    components.add(new Slider(setting, baka, setOffset));
                } else if (setting instanceof KeybindSetting) {
                    components.add(new Keybind(setting, baka, setOffset));
                } else if (setting instanceof StringSetting) {
                    components.add(new TextBox(setting, baka, setOffset));
                }
            setOffset += height;
            }
        }


        for (Component component : components) {
        component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        super.isHovered(mouseX, mouseY);
        //return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
        return mouseX > x && mouseX < x + width && mouseY > y + offset && mouseY < y + offset + height;
    }

    public void keyPressed(int key) {
        super.keyPressed(key);
        for (Component component : components) {
            component.keyPressed(key);
        }
    }

}
