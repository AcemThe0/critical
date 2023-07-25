package acme.critical.mixin;

import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import acme.critical.module.ModMan;
import acme.critical.module.visual.Norender;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {
    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    private static void onGetGrassColor(
        BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir
    ) {
        Norender norender = ModMan.INSTANCE.getMod(Norender.class);
        if (!norender.isEnabled() || !norender.grass.isEnabled())
            return;
        cir.setReturnValue(norender.grassColor.getColor());
    }
    @Inject(method = "getFoliageColor", at = @At("HEAD"), cancellable = true)
    private static void onGetFoliageColor(
        BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir
    ) {
        Norender norender = ModMan.INSTANCE.getMod(Norender.class);
        if (!norender.isEnabled() || !norender.foliage.isEnabled())
            return;
        cir.setReturnValue(norender.foliageColor.getColor());
    }
    @Inject(method = "getWaterColor", at = @At("HEAD"), cancellable = true)
    private static void onGetWaterColor(
        BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir
    ) {
        Norender norender = ModMan.INSTANCE.getMod(Norender.class);
        if (!norender.isEnabled() || !norender.water.isEnabled())
            return;
        cir.setReturnValue(norender.waterColor.getColor());
    }
}
