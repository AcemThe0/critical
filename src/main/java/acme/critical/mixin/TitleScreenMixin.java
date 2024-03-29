package acme.critical.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;

import acme.critical.ui.Hud;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    public void render(
        DrawContext context, int mouseX, int mouseY, float tickDelta,
        CallbackInfo ci
    ) {
        Hud.render(context, tickDelta);
    }
}
