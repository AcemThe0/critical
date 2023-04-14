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
		Entity entity = (Entity) (Object) this;
		if (ka.isEnabled() && entity == ka.getCurrentTarget()) cir.setReturnValue(true);
		if (!esp.isEnabled() || esp.getMode() != "Glow") return;
		if (esp.players.isEnabled() && entity.isPlayer()) cir.setReturnValue(true);
		if (esp.passive.isEnabled() && ka.getEntityType(entity.getType()) == "Passive" && !entity.isPlayer()) cir.setReturnValue(true);
		if (esp.offensive.isEnabled() && ka.getEntityType(entity.getType()) == "Offensive") cir.setReturnValue(true);
		
	}

	@Inject(method = "Lnet/minecraft/entity/Entity;getTeamColorValue()I", at = @At("HEAD"), cancellable = true)
	public void getTeamColorValue(CallbackInfoReturnable cir) {
		Entity entity = (Entity) (Object) this;
		if (esp.isEnabled()) cir.setReturnValue(esp.getColor(entity));
		if (entity == ka.getCurrentTarget()) cir.setReturnValue(new Color(127, 0, 0).getRGB());
	}
}
