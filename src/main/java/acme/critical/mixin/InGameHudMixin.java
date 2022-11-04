package acme.critical.mixin;

import acme.critical.ui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    public void renderHUD(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        Hud.render(matrices, tickDelta);
    }
}
