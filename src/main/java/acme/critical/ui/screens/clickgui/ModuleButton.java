package acme.critical.ui.screens.clickgui;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import acme.critical.module.Mod;
import acme.critical.utils.ColorUtils;
import acme.critical.module.settings.*;
import acme.critical.module.client.Clickgui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import acme.critical.ui.screens.clickgui.setting.*;
import acme.critical.ui.screens.clickgui.setting.Component;

import acme.critical.utils.Render2DUtils;

public class ModuleButton {
    public int widthOffset = MinecraftClient.getInstance().textRenderer.getWidth("Critical (JW-13.37.69)");
    public Mod module;
    public Window parent;
    public int offset;
    public boolean extended;
    public List<Component> components;
    
    public ModuleButton(Mod module, Window parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
        this.extended = false;
        this.components = new ArrayList<>();

	// hack
        if (module == null) return;

        int setOffset = parent.height;
        for (Setting setting : module.getSettings()) {
            if (setting.isLabeled()) {
	        components.add(new LabelBox(setting, this, setOffset));
		setOffset += parent.height;
	    }

            if (setting instanceof BooleanSetting) {
                components.add(new CheckBox(setting, this, setOffset));
            } else if (setting instanceof ColorSetting) {
                components.add(new ColorPicker(setting, this, setOffset));
            } else if (setting instanceof LabelSetting) {
                components.add(new LabelBox(setting, this, setOffset));
	    } else if (setting instanceof ModeSetting) {
                components.add(new ModeBox(setting, this, setOffset));
            } else if (setting instanceof NumberSetting) {
                components.add(new Slider(setting, this, setOffset));
            } else if (setting instanceof KeybindSetting) {
                components.add(new Keybind(setting, this, setOffset));
            } else if (setting instanceof StringSetting) {
                components.add(new TextBox(setting, this, setOffset)); 
            }
            setOffset += parent.height;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int c;
        if (!isHovered(mouseX, mouseY)) c = 0; else c = 2;

	if (!module.isEnabled()) {
		Render2DUtils.outset(
			context, parent.x, parent.y + offset,
			parent.x + parent.width, parent.y + offset + parent.height,
			c
		);
	} else {
		Render2DUtils.inset(
			context, parent.x, parent.y + offset,
			parent.x + parent.width, parent.y + offset + parent.height,
			c
		);
	}
        int textOffset = (parent.height/2)-parent.mc.textRenderer.fontHeight/2;
        Render2DUtils.text(context, module.getName(), parent.x + 2, parent.y + offset + textOffset);

        if (extended) {
            for (Component component : components) {
                component.render(context, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                extended = !extended;
                parent.updateButtons();
            }
        }

        for (Component component : components) {
            if (extended) component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }

    public void keyPressed(int key) {
        for (Component component : components) {
            component.keyPressed(key);
        }
    }
}
