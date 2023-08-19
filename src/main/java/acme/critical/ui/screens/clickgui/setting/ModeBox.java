package acme.critical.ui.screens.clickgui.setting;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.util.math.MatrixStack;

import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.Setting;
import acme.critical.ui.screens.clickgui.ModuleButton;
import acme.critical.utils.Render2DUtils;

public class ModeBox extends Component {
    private ModeSetting modeSet = (ModeSetting)setting;
    List<String> modes = modeSet.getModes();
    private boolean extended = false;
    int modesSize = modes.size();

    public ModeBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.modeSet = (ModeSetting)setting;
    }

    @Override
    public void
    render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Render2DUtils.rect(
            matrices, parent.parent.x, parent.parent.y + parent.offset + offset,
            parent.parent.x + parent.parent.width,
            parent.parent.y + parent.offset + offset + parent.parent.height, 0
        );
        // DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y +
        // parent.offset + offset, parent.parent.x + parent.parent.width,
        // parent.parent.y + parent.offset + offset + parent.parent.height, new
        // Color(0, 0, 0, 160).getRGB());
        int textOffset =
            ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
        Render2DUtils.text(
            matrices, modeSet.getName() + ": " + modeSet.getMode(),
            parent.parent.x + 2,
            parent.parent.y + parent.offset + offset + textOffset
        );
        if (extended) {
            // make popup render above everything else
            matrices.push();
            matrices.translate(0.0f, 0.0f, 100.0f);
            for (int indexM = 0; indexM < modesSize; indexM++) {
                Render2DUtils.rect(
                    matrices, parent.parent.x + parent.parent.width,
                    (indexM * parent.parent.height) + parent.parent.y +
                        parent.offset + offset,
                    parent.parent.x + (parent.parent.width * 2),
                    (indexM * parent.parent.height) + parent.parent.y +
                        parent.offset + offset + parent.parent.height,
                    0
                );
                Render2DUtils.text(
                    matrices, modes.get(indexM),
                    2 + parent.parent.x + parent.parent.width,
                    (indexM * parent.parent.height) + parent.parent.y +
                        parent.offset + offset + textOffset
                );
            }
            matrices.pop();
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        // if (isHovered(mouseX, mouseY) && button == 0) modeSet.cycle();
        if (isHovered(mouseX, mouseY) && button == 1)
            extended = !extended;

        if (extended && getHoveredMode(mouseX, mouseY) != -1 && button == 0) {
            modeSet.setIndex(getHoveredMode(mouseX, mouseY));
            extended = false;
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    private int getHoveredMode(double mouseX, double mouseY) {
        for (int indexM = 0; indexM < modesSize; indexM++) {
            if (mouseX > parent.parent.x + parent.parent.width &&
                mouseX < parent.parent.x + (parent.parent.width * 2) &&
                mouseY > (indexM * parent.parent.height) + parent.parent.y +
                             parent.offset + offset &&
                mouseY < (indexM * parent.parent.height) + parent.parent.y +
                             parent.offset + offset + parent.parent.height) {
                return indexM;
            }
        }
        return -1;
    }
}
