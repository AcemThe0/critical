package acme.critical.ui.screens.clickgui.setting;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget; 
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import acme.critical.utils.Render2DUtils;
import acme.critical.module.settings.Setting;
import acme.critical.module.settings.StringSetting;
import acme.critical.ui.screens.clickgui.ModuleButton;

public class TextBox extends Component {
	private StringSetting strset = (StringSetting)setting;
	boolean writing = false;

	public TextBox(Setting setting, ModuleButton parent, int offset) {
		super(setting, parent, offset);
	}

	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if (isHovered(mouseX, mouseY) && button == 0) {
			writing = !writing;
		} else { writing = false;};
	}

	@Override
	public void keyPressed(int key) {
		if (writing) {
			if (key == 256 || key == 257) { writing = false; } //ESC ; ENTER
			else if (strset.getVal().length() >= 1 && (key == 261 || key == 259)) { strset.rem(); } //DEL ; BACKSPACE
			else if ( strset.getVal().length() <= 14) { strset.add(strset.getKeyChar(key)); }
		}
	}

	int textOffset = ((parent.parent.height/2)-mc.textRenderer.fontHeight/2);

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		Render2DUtils.inset(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, 0);
		mc.textRenderer.drawWithShadow(matrices, strset.getVal() + (writing ? "_":""), parent.parent.x + 2, parent.parent.y + parent.offset + offset + textOffset, -1);
	}

}
