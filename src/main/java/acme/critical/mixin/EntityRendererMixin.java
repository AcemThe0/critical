package acme.critical.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

import acme.critical.module.ModMan;
import acme.critical.module.visual.Nametags;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Inject(
        method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true
    )
    private void
    onRenderLabelIfPresent(
        T entity, Text text, MatrixStack matrices,
        VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci
    ) {
        Nametags nametags = ModMan.INSTANCE.getMod(Nametags.class);
        if (nametags.isEnabled() && nametags.shouldDraw(entity))
            ci.cancel();
    }
}
