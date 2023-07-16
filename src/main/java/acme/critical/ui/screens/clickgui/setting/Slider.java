package acme.critical.ui.screens.clickgui.setting;

import java.awt.Color;
import acme.critical.utils.MathUtils;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.Render2DUtils;
import acme.critical.module.settings.Setting;
import net.minecraft.client.gui.DrawContext;
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
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));
	
        Render2DUtils.slider(
	    context, numSet.getValue(), numSet.getMin(), numSet.getMax(),
            parent.parent.x, parent.parent.y + parent.offset + offset,
	    parent.parent.width, parent.parent.height
        );

        if (sliding) {
            if(diff == 0) {
                numSet.setValue(numSet.getMin());
            } else {
                numSet.setValue(MathUtils.roundToPlace(((diff/parent.parent.width)*(numSet.getMax()-numSet.getMin())+numSet.getMin()), 1));
            }
        }

        int textOffset = ((parent.parent.height/2)-mc.textRenderer.fontHeight/2);
        Render2DUtils.text(context, numSet.getName() + ": " + MathUtils.roundToPlace(numSet.getValue(), 2), parent.parent.x + 2, parent.parent.y + parent.offset + offset + textOffset);
        super.render(context, mouseX, mouseY, delta);
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
