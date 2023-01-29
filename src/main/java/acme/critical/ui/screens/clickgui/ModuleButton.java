package acme.critical.ui.screens.clickgui;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import acme.critical.module.Mod;
import acme.critical.utils.ColorUtils;
import acme.critical.module.settings.*;
import acme.critical.module.client.Clickgui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import acme.critical.ui.screens.clickgui.setting.*;
import acme.critical.ui.screens.clickgui.setting.Component;

import acme.critical.utils.Render2DUtils;

public class ModuleButton {
    public int widthOffset = MinecraftClient.getInstance().textRenderer.getWidth("Critical (JW-13.37.69)");
    public Mod module;
    public Frame parent;
    public int offset;
    public boolean extended;
    public List<Component> components;
    
    public ModuleButton(Mod module, Frame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
        this.extended = false;
        this.components = new ArrayList<>();

        int setOffset = parent.height;
        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                components.add(new CheckBox(setting, this, setOffset));
            } else if (setting instanceof ModeSetting) {
                components.add(new ModeBox(setting, this, setOffset));
            } else if (setting instanceof NumberSetting) {
                components.add(new Slider(setting, this, setOffset));
            } else if (setting instanceof KeybindSetting) {
                components.add(new Keybind(setting, this, setOffset));
            }
            setOffset += parent.height;
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        //DrawableHelper.fill(  , new Color(0, 0, 0, 190).getRGB());
	Render2DUtils.rect(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, (char) 0);
        if(isHovered(mouseX, mouseY)) {
            DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0, 0, 0, 140).getRGB());

            if (Clickgui.descriptions.isEnabled()) {
                int startOffset = MinecraftClient.getInstance().textRenderer.getWidth(module.getDesc())+3+widthOffset;
                DrawableHelper.fill(matrices, widthOffset, 1, startOffset, MinecraftClient.getInstance().textRenderer.fontHeight+3, new Color(0, 0, 0, 160).getRGB());
                DrawableHelper.fill(matrices, startOffset, 1, startOffset+2, MinecraftClient.getInstance().textRenderer.fontHeight+3, ColorUtils.contrast());
                parent.mc.textRenderer.drawWithShadow(matrices, module.getDesc(), widthOffset+2, 3, -1);
            }
        }
        int textOffset = (parent.height/2)-parent.mc.textRenderer.fontHeight/2;
        parent.mc.textRenderer.drawWithShadow(matrices, module.getName(), parent.x + 2, parent.y + offset + textOffset, module.isEnabled() ? -1 : new Color(255, 255, 255, 255).getRGB());

        if (extended) {
            for (Component component : components) {
                component.render(matrices, mouseX, mouseY, delta);
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
