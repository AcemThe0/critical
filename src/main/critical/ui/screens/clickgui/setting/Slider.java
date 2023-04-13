package acme.critical.ui.screens.clickgui.setting;

import java.awt.Color;
import acme.critical.utils.MathUtils;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.Render2DUtils;
import acme.critical.module.settings.Setting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import acme.critical.module.settings.NumberSetting;
import acme.critical.ui.screens.clickgui.ModuleButton;

public class Slider extends Component{
    public NumberSetting numSet = (NumberSetting)setting;
    private boolean sliding = false;

    public Slider(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.numSet = (NumberSetting)setting;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0, 0, 0, 160).getRGB());
        Render2DUtils.rect(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, 0);
        
        double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));
        int renderWidth = (int)(parent.parent.width * (numSet.getValue() - numSet.getMin()) / (numSet.getMax() - numSet.getMin()));
        //DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset+2, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height-2, ColorUtils.contrast());
        Render2DUtils.inset(matrices,
        parent.parent.x, parent.parent.y + parent.offset + offset+6,
        parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height-6,
        0);
        Render2DUtils.rect(matrices,
        parent.parent.x+renderWidth-2, parent.parent.y + parent.offset + offset+2,
        parent.parent.x+renderWidth+2, parent.parent.y + parent.offset + offset + parent.parent.height-2,
        0);

        if (sliding) {
            if(diff == 0) {
                numSet.setValue(numSet.getMin());
            } else {
                numSet.setValue(MathUtils.roundToPlace(((diff/parent.parent.width)*(numSet.getMax()-numSet.getMin())+numSet.getMin()), 1));
            }
        }

        int textOffset = ((parent.parent.height/2)-mc.textRenderer.fontHeight/2);
        mc.textRenderer.drawWithShadow(matrices, numSet.getName() + ": " + MathUtils.roundToPlace(numSet.getValue(), 2), parent.parent.x + 2, parent.parent.y + parent.offset + offset + textOffset, -1);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) sliding = true;
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        sliding = false;
        super.mouseReleased(mouseX, mouseY, button);
    }
}
