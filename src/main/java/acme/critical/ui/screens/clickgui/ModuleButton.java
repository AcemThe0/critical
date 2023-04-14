package acme.critical.ui.screens.clickgui;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.utils.ColorUtils;
import acme.critical.module.settings.*;
import acme.critical.utils.Render2DUtils;
import acme.critical.module.client.Clickgui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import acme.critical.ui.screens.clickgui.setting.*;

public class ModuleButton {
    public int widthOffset = MinecraftClient.getInstance().textRenderer.getWidth("Critical (JW-13.37.69)");
    public Mod module;
    public Window parent;
    public int offset;
    public boolean extended;
    
    public ModuleButton(Mod module, Window parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
        this.extended = false;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int c;
        if (!isHovered(mouseX, mouseY)) c = 0; else c = 2;

	if (!module.isEnabled()) {
		Render2DUtils.rect(
			matrices, parent.x, parent.y + offset,
			parent.x + parent.width, parent.y + offset + parent.height,
			c
		);
	} else {
		Render2DUtils.inset(
			matrices, parent.x, parent.y + offset,
			parent.x + parent.width, parent.y + offset + parent.height,
			c
		);
	}
        int textOffset = (parent.height/2)-parent.mc.textRenderer.fontHeight/2;
        parent.mc.textRenderer.drawWithShadow(matrices, module.getName(), parent.x + 2, parent.y + offset + textOffset, module.isEnabled() ? -1 : new Color(255, 255, 255, 255).getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                ModMan.INSTANCE.selectedModule = module;
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {

    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }

    public void keyPressed(int key) {

    }

    public void changeModule(Mod mod) {
        module = mod;
    }
}
