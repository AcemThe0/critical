package acme.critical.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.world.entity.EntityLike;

import acme.critical.module.ModMan;
import acme.critical.module.visual.ESP;
import acme.critical.module.combat.Killaura;

import java.awt.Color;

@Mixin(Entity.class)
public abstract class EntityMixin implements CommandOutput, Nameable, EntityLike {
	ESP esp = ModMan.INSTANCE.getMod(ESP.class);
	Killaura ka = ModMan.INSTANCE.getMod(Killaura.class);
	@Inject(method = "Lnet/minecraft/entity/Entity;isGlowing()Z", at = @At("HEAD"), cancellable = true)
	public void isGlowing(CallbackInfoReturnable cir) {
		if (ka.isEnabled() && (Entity) (Object) this == ka.getCurrentTarget()) cir.setReturnValue(true);
		if (!esp.isEnabled() || esp.getMode() != "Glow") return;
		if (!esp.getJustPlayers() || (Entity) (Object) this instanceof PlayerEntity)
			cir.setReturnValue(true);
	}

	@Inject(method = "Lnet/minecraft/entity/Entity;getTeamColorValue()I", at = @At("HEAD"), cancellable = true)
	public void getTeamColorValue(CallbackInfoReturnable cir) {
		if (esp.isEnabled()) cir.setReturnValue(esp.getColor((Entity) (Object) this));
		if ((Entity) (Object) this == ka.getCurrentTarget()) cir.setReturnValue(new Color(127, 0, 0).getRGB());
	}
}
