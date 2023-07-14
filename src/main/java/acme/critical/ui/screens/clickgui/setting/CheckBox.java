package acme.critical.ui.screens.clickgui.setting;

import java.awt.Color;
import acme.critical.utils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import acme.critical.module.settings.Setting;
import net.minecraft.client.gui.DrawContext;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.ui.screens.clickgui.ModuleButton;

import acme.critical.utils.Render2DUtils;

public class CheckBox extends Component {

    private BooleanSetting boolSet = (BooleanSetting)setting;

    public CheckBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.boolSet = (BooleanSetting)setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Render2DUtils.rect(context, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, (char) 0);
        int textOffset = ((parent.parent.height/2)-mc.textRenderer.fontHeight/2);
        Render2DUtils.text(context, boolSet.getName(), parent.parent.x + 2, parent.parent.y + parent.offset + offset + textOffset);
        /*DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0, 0, 0, 160).getRGB());*/


        /*DrawableHelper.fill(matrices, parent.parent.x + parent.parent.width-16, parent.parent.y+parent.offset+offset+1, parent.parent.x + parent.parent.width-2, parent.parent.y + parent.offset + offset + parent.parent.height-1, Color.black.getRGB());
        DrawableHelper.fill(matrices, parent.parent.x + parent.parent.width-14, parent.parent.y+parent.offset+offset+3, parent.parent.x + parent.parent.width-4, parent.parent.y + parent.offset + offset + parent.parent.height-3, boolSet.isEnabled() ? ColorUtils.contrast() : Color.black.getRGB());*/
        if (boolSet.isEnabled()) {
            Render2DUtils.inset(
		context,
		parent.parent.x + parent.parent.width - 15, parent.parent.y + parent.offset + offset + 3,
        parent.parent.x + parent.parent.width - 5, parent.parent.y + parent.offset + offset + parent.parent.height - 3,
        0
            );
        } else {
            Render2DUtils.rect(
		context,
		parent.parent.x + parent.parent.width - 15, parent.parent.y + parent.offset + offset + 3,
        parent.parent.x + parent.parent.width - 5, parent.parent.y + parent.offset + offset + parent.parent.height - 3,
        0
            );
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if(isHovered(mouseX, mouseY) && button == 0) {boolSet.toggle();}
        super.mouseClicked(mouseX, mouseY, button);
    }
}
