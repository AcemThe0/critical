package acme.critical.mixin;

import acme.critical.Critical;
import acme.critical.ui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import acme.critical.module.ModMan;
import acme.critical.module.visual.Norender;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/util/Identifier;F)V", ordinal = 0))
    private void onRenderPumpkinOverlay(Args args) {
	Norender norender = ModMan.INSTANCE.getMod(Norender.class);
	if (norender.isEnabled() & norender.pumpkinEnabled()) {
	        args.set(1, 0f);
	}
    }

    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    public void renderHUD(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
	Critical.INSTANCE.onRender2D(matrices, tickDelta);
        Hud.render(matrices, tickDelta);
    }
}
