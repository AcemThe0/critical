package acme.critical.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;

import acme.critical.Critical;
import acme.critical.module.ModMan;
import acme.critical.module.visual.Norender;
import acme.critical.ui.Hud;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    public void
    onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        Critical.INSTANCE.onRender2D(matrices, tickDelta);
        Hud.render(matrices, tickDelta);
    }

    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    public void onRenderOverlay(
        MatrixStack matrices, Identifier texture, float opacity, CallbackInfo ci
    ) {
        Norender norender = ModMan.INSTANCE.getMod(Norender.class);
        if (norender.isEnabled() && norender.pumpkin.isEnabled() &&
            texture.getPath().equals("textures/misc/pumpkinblur.png"))
            ci.cancel();
    }
}
