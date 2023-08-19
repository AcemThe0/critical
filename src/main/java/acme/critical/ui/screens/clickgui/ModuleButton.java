package acme.critical.ui.screens.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import acme.critical.module.Mod;
import acme.critical.module.client.Clickgui;
import acme.critical.module.settings.*;
import acme.critical.ui.screens.clickgui.setting.*;
import acme.critical.ui.screens.clickgui.setting.Component;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.Render2DUtils;

public class ModuleButton {
    public Mod module;
    public Window parent;
    public int offset;

    public ModuleButton(Mod module, Window parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
    }

    public void
    render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (isHovered(mouseX, mouseY))
            Render2DUtils.text(matrices, module.getDesc(), 4, 4);

        if (!module.isEnabled()) {
            Render2DUtils.rect(
                matrices, parent.x, parent.y + offset, parent.x + parent.width,
                parent.y + offset + parent.height,
                isHovered(mouseX, mouseY) ? 2 : 0
            );
        } else {
            Render2DUtils.inset(
                matrices, parent.x, parent.y + offset, parent.x + parent.width,
                parent.y + offset + parent.height,
                isHovered(mouseX, mouseY) ? 2 : 0
            );
        }
        int textOffset =
            (parent.height / 2) - parent.mc.textRenderer.fontHeight / 2;
        Render2DUtils.text(
            matrices, module.getName(), parent.x + 2,
            parent.y + offset + textOffset
        );
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (!isHovered(mouseX, mouseY))
            return;

        if (button == 0)
            module.toggle();
        else if (button == 1)
            ClickGUI.INSTANCE.SelectedMod = module;
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {}

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width &&
            mouseY > parent.y + offset &&
            mouseY < parent.y + offset + parent.height;
    }

    public void keyPressed(int key) {}
}
