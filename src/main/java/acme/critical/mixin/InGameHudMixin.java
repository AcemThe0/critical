package acme.critical.mixin;

import acme.critical.Critical;
import acme.critical.ui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import acme.critical.module.ModMan;
import acme.critical.module.visual.Norender;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    public void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
	Critical.INSTANCE.onRender2D(context, tickDelta);
        Hud.render(context, tickDelta);
    }

    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    public void onRenderOverlay(DrawContext context, Identifier texture, float opacity, CallbackInfo ci) {
        Norender norender = ModMan.INSTANCE.getMod(Norender.class);
        if (norender.isEnabled() && norender.pumpkin.isEnabled()
            && texture.getPath().equals("textures/misc/pumpkinblur.png")
        ) ci.cancel();
    }
}
