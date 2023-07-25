package acme.critical.mixin;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;

import acme.critical.Critical;
import acme.critical.event.events.EventWorldRender;
import acme.critical.utils.Render3DUtils;

import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "renderWorld", at = @At("HEAD"))
    private void onRenderWorldPre(
        float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci
    ) {
        Critical.eventBus.post(
            new EventWorldRender.Pre(tickDelta, limitTime, matrices)
        );
    }

    @Inject(
        method = "renderWorld",
        at = @At(
            // called right before rendering ends
            value = "FIELD",
            target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
            opcode = Opcodes.GETFIELD, ordinal = 0
        )
    )
    private void
    onRenderWorldPost(
        float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci
    ) {
        Critical.eventBus.post(
            new EventWorldRender.Post(tickDelta, limitTime, matrices)
        );
    }
}
