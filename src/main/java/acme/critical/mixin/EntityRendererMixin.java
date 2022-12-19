package acme.critical.mixin;

import acme.critical.module.ModMan;
import acme.critical.module.visual.Nametags;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

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

	@Inject(
		at = {@At("HEAD")},
		method = {"renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
		cancellable = true
	)
	private void onRenderLabelIfPresent(
		T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
		int light, CallbackInfo ci
	) {
		Nametags nametags = ModMan.INSTANCE.getMod(Nametags.class);

		if (!nametags.isEnabled())
			return;

		matrices.push();
		matrices.translate(0.0d, entity.getHeight() + 0.5f, 0.0d);
		matrices.multiply(this.dispatcher.getRotation());

		float scale = nametags.getSize() * 0.1f;
		float scale_dist = MinecraftClient.getInstance().player.distanceTo(entity) / 10;
		if (scale_dist > 1.0f)
			scale *= scale_dist;
		matrices.scale(-scale, -scale, scale);

		Matrix4f posmat = matrices.peek().getPositionMatrix();
		TextRenderer textRenderer = this.getTextRenderer();

		DrawableHelper.fill(
			matrices,
			-textRenderer.getWidth(text) / 2, 0,
			textRenderer.getWidth(text) / 2, textRenderer.fontHeight,
			new Color(0, 0, 0, 160).getRGB()
		);

		textRenderer.draw(
			text,
			-textRenderer.getWidth(text) / 2, 0,
			new Color(255, 255, 255, 255).getRGB(), false, posmat, vertexConsumers, true, 0, 15
		);

		matrices.pop();

		ci.cancel();
	}

	// this will appease the compiler
	@Shadow
	public TextRenderer getTextRenderer()
	{
		return null;
	}
}
