package acme.critical.ui.screens.clickgui.setting;

import java.awt.Color;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.Setting;
import acme.critical.ui.screens.clickgui.ModuleButton;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.MathUtils;
import acme.critical.utils.Render2DUtils;

public class Slider extends Component {
    public NumberSetting numSet = (NumberSetting)setting;
    private boolean sliding = false;

    public Slider(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.numSet = (NumberSetting)setting;
    }

    @Override
    public void
    render(DrawContext context, int mouseX, int mouseY, float delta) {
        double diff = Math.min(
            parent.parent.width, Math.max(0, mouseX - parent.parent.x)
        );

        Render2DUtils.slider(
            context, numSet.getValue(), numSet.getMin(), numSet.getMax(),
            parent.parent.x, parent.parent.y + parent.offset + offset,
            parent.parent.width, parent.parent.height
        );
        Render2DUtils.rect(
            context,
            parent.parent.x+parent.parent.width,
            parent.parent.y+parent.offset+offset,
            parent.parent.width+parent.parent.x+parent.parent.width,
            parent.parent.height+parent.parent.y+parent.offset+offset,
            0
        );

        int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);

        Render2DUtils.text(
            context,
            numSet.getName(),
            parent.parent.x + parent.parent.width + 2,
            parent.parent.y + parent.offset + offset + textOffset
        );

        if (sliding) {
            Render2DUtils.text(
                context,
                ": "+MathUtils.roundToPlace(numSet.getValue(), 2),
                (parent.parent.x + parent.parent.width + 2) + mc.textRenderer.getWidth(numSet.getName()),
                parent.parent.y + parent.offset + offset + textOffset
            );

            if (diff == 0) {
                numSet.setValue(numSet.getMin());
            } else {
                numSet.setValue(MathUtils.roundToPlace(
                    ((diff / parent.parent.width) *
                         (numSet.getMax() - numSet.getMin()) +
                     numSet.getMin()),
                    1
                ));
            }
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY))
            sliding = true;
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        sliding = false;
        super.mouseReleased(mouseX, mouseY, button);
    }
}
