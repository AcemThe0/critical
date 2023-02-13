package acme.critical.ui.screens.clickgui;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.module.Mod.Category;
import net.minecraft.client.MinecraftClient;
import acme.critical.module.client.Clickgui;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import acme.critical.ui.screens.clickgui.setting.Component;

import acme.critical.utils.Render2DUtils;

public class Frame {
    
    public int x, y, width, height, dragX, dragY;
    public Category category;

    public boolean dragging, extended;
    public static int draggedElements;
    private List<ModuleButton> buttons;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Frame(Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.extended = false;

        buttons = new ArrayList<>();
        int offset = height;
        for (Mod mod : ModMan.INSTANCE.getModulesInCategory(category)) {
            buttons.add(new ModuleButton(mod, this, offset));
            offset += height;
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int offset = (height/2)-mc.textRenderer.fontHeight/2;
	Render2DUtils.rect(matrices, x, y, x + width, y + height, 1);
        mc.textRenderer.drawWithShadow(matrices, category.name, x+2, y+offset, -1);

        if (extended) {
            for (ModuleButton button : buttons) {
                button.render(matrices, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                draggedElements += 1;
                if (draggedElements == 1) dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            } else if (button == 1) {
                extended = !extended;
            }
        }

        if (extended) {
            for (ModuleButton mb : buttons) {
                mb.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging == true) {
            dragging = false;
            draggedElements = 0;
        }

        for (ModuleButton mb : buttons) {
            mb.mouseReleased(mouseX, mouseY, button);
        }
    }

    public void keyPressed(int key) {
        for (ModuleButton mb : buttons) {
            mb.keyPressed(key);
        }
        
        if ((key == 265 || key == 264) && Clickgui.arrows.isEnabled()) scroll(key, 25);
    }

    public void scroll(int keyCode, int px) {
        int pixels = 0;
        //23*px(25) = 575, MUST change if fully extended module size changes, jank
        if (keyCode == 265 && y >= -575) {
            pixels = -px;
        } else if (keyCode == 264 && y <= 575) {
            pixels = px;
        }
        y += pixels;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void updatePosition(double mouseX, double mouseY) {
        if (dragging) {
            x = (int) (mouseX - dragX);
            y = (int) (mouseY - dragY);
        }
    }

    public void updateButtons() {
        int offset = height;

        for (ModuleButton button : buttons) {
            button.offset = offset;
            offset += height;

            if (button.extended) {
                for (Component component : button.components) {
                    if (component.setting.isVisible()) offset += height;
                }
            }
        }
    }
}
