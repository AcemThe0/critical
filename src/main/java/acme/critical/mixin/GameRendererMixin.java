package acme.critical.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;


import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilder.BuiltBuffer;
import net.minecraft.client.render.Camera;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Inject(method = "renderWorld", at = @At("HEAD"))
	private void onRenderWorldPre(float tickDelta, long limitTime, MatrixStack matrices) {
	}

	@Inject(method = "renderWorld", at = @At(
			// called right before rendering ends
			value = "FIELD",
			target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
			opcode = Opcodes.GETFIELD,
			ordinal = 0
		)
	)
	private void onRenderWorldPost(float tickDelta, long limitTime, MatrixStack matrices) {
		/*Tessellator tes = Tessellator.getInstance();
		tes.getBuffer().begin(DrawMode.LINES, VertexFormats.POSITION_COLOR);
		tes.getBuffer().vertex(0, 0, 0).color(255, 255, 255, 255).next();
		tes.getBuffer().vertex(1, 1, 0).color(255, 255, 255, 255).next();
		tes.draw();*/
	}


}
