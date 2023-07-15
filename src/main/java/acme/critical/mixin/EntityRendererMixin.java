package acme.critical.mixin;

import acme.critical.module.ModMan;
import acme.critical.module.visual.Nametags;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin <T extends Entity> {
	@Shadow
	@Final
	protected EntityRenderDispatcher dispatcher;

	@Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
	private void onRenderLabelIfPresent(
		T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
		int light, CallbackInfo ci
	) {
		Nametags nametags = ModMan.INSTANCE.getMod(Nametags.class);

		if (!nametags.isEnabled())
			return;

		matrices.push();
		matrices.translate(0.0, entity.getHeight() + 0.5, 0.0);
		matrices.multiply(this.dispatcher.getRotation());

		float scale = nametags.getSize() * 0.01f;
		float scale_dist = MinecraftClient.getInstance().player.distanceTo(entity) / 10;
		if (scale_dist > 1.0f)
			scale *= scale_dist;
		matrices.scale(-scale, -scale, scale);

		Matrix4f posmat = matrices.peek().getPositionMatrix();
		TextRenderer textRenderer = this.getTextRenderer();

		String text_format = text.getString();
		if (entity instanceof LivingEntity) {
			short hp = (short)(((LivingEntity)entity).getHealth());
			text_format = text_format + " \u00a7a" + hp;
		}

		// prevent zclipping with background
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		// background
		textRenderer.draw(
			text_format,
			-textRenderer.getWidth(text_format) / 2, 0,
			0xffffffff, false, posmat, vertexConsumers,
			TextLayerType.NORMAL, 0xa0000000, 15
		);
		// text
		textRenderer.draw(
			text_format,
			-textRenderer.getWidth(text_format) / 2, 0,
			0xffffffff, false, posmat, vertexConsumers,
			TextLayerType.SEE_THROUGH, 0x00000000, 15
		);

		GL11.glEnable(GL11.GL_DEPTH_TEST);

		matrices.pop();

		ci.cancel();
	}

	// this will appease the compiler
	@Shadow
	public TextRenderer getTextRenderer() {
		return null;
	}
}
