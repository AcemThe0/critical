package acme.critical.ui.screens.clickgui.setting;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget; 
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import acme.critical.module.settings.Setting;
import acme.critical.module.settings.StringSetting;
import acme.critical.ui.screens.clickgui.ModuleButton;

public class TextBox extends Component {
	private StringSetting strset;// = (StringSetting) setting;
	private TextFieldWidget field;// = new TextFieldWidget(

	public TextBox(Setting setting, ModuleButton parent, int offset) {
		super(setting, parent, offset);
		this.strset = (StringSetting) setting;

		field = new TextFieldWidget(
			MinecraftClient.getInstance().textRenderer,
			parent.parent.x,
			parent.parent.y + parent.offset + offset,
			256,
			256,
			Text.of(strset.getVal())
		);
		field.setEditable(true);
		//field.setFocusUnlocked(true);
		field.setTextFieldFocused(true);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		/*Render2DUtils.rect(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, 0);*/

		field.tick();
		field.renderButton(matrices, mouseX, mouseY, delta);

	}

}
