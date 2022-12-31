package acme.critical.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.option.KeyBinding;

import acme.critical.mixinterface.KeyBindingInterface;

@Mixin(KeyBinding.class)
public class KeyBindingMixin implements KeyBindingInterface {
	@Shadow
	private int timesPressed;

	@Override
	public int getTimesPressed() {
		return timesPressed;
	}

	@Override
	public void setTimesPressed(int num) {
		timesPressed = num;
	}

	@Override
	public void release() {
		timesPressed = 0;
		((KeyBinding) (Object) this).setPressed(false);
	}
}
