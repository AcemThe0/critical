package acme.critical.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import acme.critical.module.ModMan;
import acme.critical.module.movement.Slip;
import acme.critical.module.visual.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side,
            BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        Xray xray = ModMan.INSTANCE.getMod(Xray.class);
        if (xray.isEnabled()) {
            cir.setReturnValue(Xray.blocks.contains(state.getBlock()));
        }
    }

    // why does this crash on startup?
    /*
     * @Inject(method = "isTransparent", at = @At("HEAD"), cancellable = true)
     * public void isTransparent(BlockState state, BlockView world, BlockPos pos,
     * CallbackInfoReturnable<Boolean> cir) {
     * Xray xray = ModMan.INSTANCE.getMod(Xray.class);
     * if (xray.isEnabled()) {
     * cir.setReturnValue(!Xray.blocks.contains(state.getBlock()));
     * }
     * }
     */

    @Inject(method = "getSlipperiness", at = @At("RETURN"), cancellable = true)
    public void getSlipperiness(CallbackInfoReturnable<Float> info) {
        Slip slip = ModMan.INSTANCE.getMod(Slip.class);
        if (slip.isEnabled()) {
            info.setReturnValue(slip.getFriction());
        }
    }
}
