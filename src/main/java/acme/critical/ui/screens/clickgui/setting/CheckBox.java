package acme.critical.ui.screens.clickgui.setting;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import acme.critical.module.settings.Setting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.ui.screens.clickgui.ModuleButton;

public class CheckBox extends Component {

    private BooleanSetting boolSet = (BooleanSetting)setting;

    public CheckBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.boolSet = (BooleanSetting)setting;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int textOffset = ((parent.parent.height/2)-mc.textRenderer.fontHeight/2);
        mc.textRenderer.drawWithShadow(matrices, boolSet.getName(), parent.parent.x + 2, parent.parent.y + parent.offset + offset + textOffset, -1);
        DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0, 0, 0, 160).getRGB());

        DrawableHelper.fill(matrices, parent.parent.x + parent.parent.width-16, parent.parent.y+parent.offset+offset+1, parent.parent.x + parent.parent.width-2, parent.parent.y + parent.offset + offset + parent.parent.height-1, Color.black.getRGB());
        DrawableHelper.fill(matrices, parent.parent.x + parent.parent.width-14, parent.parent.y+parent.offset+offset+3, parent.parent.x + parent.parent.width-4, parent.parent.y + parent.offset + offset + parent.parent.height-3, boolSet.isEnabled() ? new Color(0, 100, 255, 160).getRGB() : Color.black.getRGB());
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if(isHovered(mouseX, mouseY) && button == 0) {boolSet.toggle();}
        super.mouseClicked(mouseX, mouseY, button);
    }
}
