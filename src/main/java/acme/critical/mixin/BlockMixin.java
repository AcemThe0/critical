package acme.critical.mixin;

import net.minecraft.block.Block;
import acme.critical.module.ModMan;
import net.minecraft.world.BlockView;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import acme.critical.module.visual.Xray;
import acme.critical.module.movement.Slip;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    
    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        Xray xray = ModMan.INSTANCE.getMod(Xray.class);
        if (xray.isEnabled()) {
            cir.setReturnValue(Xray.blocks.contains(state.getBlock()));
            }
        }

        @Inject(method = "isTranslucent", at = @At("HEAD"), cancellable = true)
        public void isTranslucent(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
            Xray xray = ModMan.INSTANCE.getMod(Xray.class);
            if (xray.isEnabled()) {
                cir.setReturnValue(!Xray.blocks.contains(state.getBlock()));
            }
        }

        @Inject(method = "getSlipperiness", at = @At("RETURN"), cancellable = true)
        public void getSlipperiness(CallbackInfoReturnable<Float> info) {
             Slip slip = ModMan.INSTANCE.getMod(Slip.class);
             if (slip.isEnabled()) {
                 info.setReturnValue(slip.getFriction());
             }
        }
}
