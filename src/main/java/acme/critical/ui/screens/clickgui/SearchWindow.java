package acme.critical.ui.screens.clickgui;

import java.util.Arrays;

import net.minecraft.client.util.math.MatrixStack;

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
	private Mod[] presults;

	public SearchWindow(int x, int y, int width, int height) {
		super("Search", x, y, width, height);

		stringsetting = new StringSetting("Search", "");
		dummy = new ModuleButton(null, this, height);
		textbox = new TextBox(stringsetting, dummy, 0);
		// make it selected by default
		textbox.writing = true;
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		if (!extended) return;

		textbox.render(matrices, mouseX, mouseY, delta);

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
		textbox.mouseClicked(mouseX, mouseY, button);
	}

	public void updateButtons() {
		int offset = height + 15;

		for (ModuleButton button : buttons) {
			button.offset = offset;
			offset += height;

			if (button.extended) {
				for (Component component : button.components)
					if (component.setting.isVisible()) offset += height;
			}
		}
	}
}
