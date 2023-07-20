package acme.critical.ui.screens.clickgui.setting;

import net.minecraft.client.gui.DrawContext;

import acme.critical.module.settings.Setting;
import acme.critical.ui.screens.clickgui.ModuleButton;
import acme.critical.utils.Render2DUtils;

public class LabelBox extends Component {
    private String title = "";

    public LabelBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.title = setting.getName() + ":";
    }

    @Override
    public void
    render(DrawContext context, int mouseX, int mouseY, float delta) {
        Render2DUtils.rect(
            context, parent.parent.x, parent.parent.y + parent.offset + offset,
            parent.parent.x + parent.parent.width,
            parent.parent.y + parent.offset + offset + parent.parent.height, 0
        );
        int textOffset =
            ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
        Render2DUtils.text(
            context, title, parent.parent.x + 2,
            parent.parent.y + parent.offset + offset + textOffset
        );
    }
}
