package acme.critical.ui.screens.clickgui;

import java.util.Arrays;

import net.minecraft.client.gui.DrawContext;

import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.module.settings.StringSetting;
import acme.critical.ui.screens.clickgui.setting.Component;
import acme.critical.ui.screens.clickgui.setting.TextBox;

public class SearchWindow extends Window {
    private StringSetting stringsetting;
    private TextBox textbox;

    private ModuleButton dummy;

    private String psearch;

    public SearchWindow(int x, int y, int width, int height) {
        super("Search", x, y, width, height);

        stringsetting = new StringSetting("Search", "");
        dummy = new ModuleButton(null, this, height);
        textbox = new TextBox(stringsetting, dummy, 0);
    }

    public void
    render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (!extended)
            return;

        textbox.render(context, mouseX, mouseY, delta);

        if (stringsetting.getVal() != psearch) {
            psearch = stringsetting.getVal();

            Mod[] results = ModMan.INSTANCE.modSearch(psearch, 10);

            super.buttons.clear();
            int offset = height + 15;
            for (Mod mod : results) {
                super.buttons.add(new ModuleButton(mod, this, offset));
                offset += height;
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (!extended)
            return;
        textbox.mouseClicked(mouseX, mouseY, button);
    }

    public void updateButtons() {
        int offset = height + 15;

        for (ModuleButton button : buttons) {
            button.offset = offset;
            offset += height;
        }
    }
}
