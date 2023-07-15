package acme.critical.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.DrawContext;

import acme.critical.ui.Hud;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "render", at = @At("RETURN"), cancellable = true)
	public void render(DrawContext context, int mouseX, int mouseY, float tickDelta, CallbackInfo ci) {
		Hud.render(context, tickDelta);
	}
}
