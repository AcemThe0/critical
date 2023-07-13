package acme.critical.ui.screens.clickgui.setting;

import java.awt.Color;
import java.util.HashMap;
import net.minecraft.util.Identifier;
import acme.critical.utils.Render2DUtils;
import acme.critical.module.settings.Setting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.ui.screens.clickgui.ModuleButton;

public class Keybind extends Component {
    private KeybindSetting binding = (KeybindSetting)setting;
    public boolean isBinding = false;

    public Keybind(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            binding.toggle();
            isBinding = true;
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void keyPressed(int key) {
        if (isBinding) {
            parent.module.setKey(key);
            binding.setKey(key);
            isBinding = false;
        }
        if ((binding.getKey() == 256 || binding.getKey() == 259)) {
            parent.module.setKey(0);
            binding.setKey(0);
            isBinding = false;
        }
        super.keyPressed(key);
    }

    int textOffset = ((parent.parent.height/2)-mc.textRenderer.fontHeight/2);
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Render2DUtils.rect(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, 0);
        //DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0, 0, 0, 175).getRGB());
        mc.textRenderer.drawWithShadow(matrices, isBinding ? "Binding..." : "Keybind: " + binding.getKeyChar(binding.getKey()), parent.parent.x + 2, parent.parent.y + parent.offset + offset + textOffset, -1);

        super.render(matrices, mouseX, mouseY, delta);
    }
}
