package acme.critical.mixin;

import net.minecraft.block.AbstractBlock;

import acme.critical.module.ModMan;
import acme.critical.module.visual.Xray;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    public void getLuminance(CallbackInfoReturnable<Integer> cir) {
        Xray xray = ModMan.INSTANCE.getMod(Xray.class);
        if (xray.isEnabled())
            cir.setReturnValue(15);
    }
}