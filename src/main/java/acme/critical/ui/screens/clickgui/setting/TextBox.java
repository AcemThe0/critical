package acme.critical.ui.screens.clickgui.setting;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget; 
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventKeyboard;
import acme.critical.module.settings.Setting;
import acme.critical.module.settings.StringSetting;
import acme.critical.ui.screens.clickgui.ModuleButton;
import acme.critical.utils.Render2DUtils;

public class TextBox extends Component {
	private StringSetting strset = (StringSetting)setting;
	boolean writing = false;

	public TextBox(Setting setting, ModuleButton parent, int offset) {
		super(setting, parent, offset);
		Critical.INSTANCE.eventBus.subscribe(this);
	}

	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if (isHovered(mouseX, mouseY) && button == 0) {
			writing = !writing;
		} else { writing = false; };
	}

	// NNNNN IM NUTS
	@Override
	public void keyPressed(int key) {}

	@CriticalSubscribe
	public void onKey(EventKeyboard event) {
		if (!writing || !event.isPressing()) return;
		if (event.isDel() && strset.getVal().length() >= 1) {
			strset.rem();
			return;
		}
		if (strset.getVal().length() <= 14) strset.add(event.getKeyReadable());
	}

	int textOffset = ((parent.parent.height/2)-mc.textRenderer.fontHeight/2);

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		Render2DUtils.inset(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, 0);
		mc.textRenderer.drawWithShadow(matrices, strset.getVal() + (writing ? "_":""), parent.parent.x + 2, parent.parent.y + parent.offset + offset + textOffset, -1);
	}

}
