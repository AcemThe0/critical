package acme.critical.ui.screens.clickgui.setting;

import java.lang.Integer;
import java.time.Instant;

import net.minecraft.client.gui.DrawContext;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventKeyboard;
import acme.critical.module.settings.ColorSetting;
import acme.critical.module.settings.Setting;
import acme.critical.ui.screens.clickgui.ModuleButton;
import acme.critical.utils.Render2DUtils;

public class ColorPicker extends Component {
    private ColorSetting colset;
    private String buffer = "#";
    private boolean writing = false;

    public ColorPicker(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        colset = (ColorSetting)setting;
        Critical.INSTANCE.eventBus.subscribe(this);
        buffer = toString(colset.getColor());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0)
            writing = !writing;
        else
            writing = false;
    }

    @Override
    public void keyPressed(int key) {}

    @CriticalSubscribe
    public void onKey(EventKeyboard.Cgui event) {
        if (!writing || !event.isPressing())
            return;
        if (event.isDel() && buffer.length() > 1) {
            buffer = buffer.substring(0, buffer.length() - 1);
            return;
        }
        char c = event.getKeyReadable();
        if (c == '\0')
            return;
        if (c >= 'A' && c <= 'F')
            c += 32;
        if (buffer.length() < 7 &&
            ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')))
            buffer += c;
        colset.setColorCGUI(toColor(buffer));
    }

    int toColor(String string) { return Integer.decode(string) | 0xff000000; }

    String toString(int color) {
        return String.format("#%06X", color & 0xffffff).toLowerCase();
    }

    int textOffset =
        ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);

    @Override
    public void
    render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (colset.updated) {
            buffer = toString(colset.getColor());
            colset.updated = false;
        }
        Render2DUtils.inset(
            context, parent.parent.x, parent.parent.y + parent.offset + offset,
            parent.parent.x + parent.parent.width,
            parent.parent.y + parent.offset + offset + parent.parent.height, 0
        );
        Render2DUtils.coloredRect(
            context,
            parent.parent.x + parent.parent.width - parent.parent.height + 2,
            parent.parent.y + parent.offset + offset + 2,
            parent.parent.x + parent.parent.width - 2,
            parent.parent.y + parent.offset + offset + parent.parent.height - 2,
            colset.getColor()
        );
        Render2DUtils.text(
            context,
            buffer + ((writing && (Instant.now().getEpochSecond() % 2 == 1))
                          ? "_"
                          : ""),
            parent.parent.x + 2,
            parent.parent.y + parent.offset + offset + textOffset
        );
    }
}
