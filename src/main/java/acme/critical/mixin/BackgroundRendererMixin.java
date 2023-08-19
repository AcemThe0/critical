package acme.critical.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;

import acme.critical.module.ModMan;
import acme.critical.module.visual.Norender;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void onApplyFog(
        Camera camera, BackgroundRenderer.FogType fogType, float viewDistance,
        boolean thickFog, float tickDelta, CallbackInfo info
    ) {
        Norender norender = ModMan.INSTANCE.getMod(Norender.class);
        if (!norender.isEnabled() || !norender.fog.isEnabled())
            return;
        RenderSystem.setShaderFogStart(viewDistance * 148.8f);
        RenderSystem.setShaderFogEnd(viewDistance * 188.9f);
    }
}
