package acme.critical.mixin;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.world.entity.EntityLike;

import acme.critical.module.ModMan;
import acme.critical.module.combat.Killaura;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin
    implements CommandOutput, Nameable, EntityLike {
    Killaura ka = ModMan.INSTANCE.getMod(Killaura.class);

    @Inject(
        method = "Lnet/minecraft/entity/Entity;isGlowing()Z", at = @At("HEAD"),
        cancellable = true
    )
    public void isGlowing(CallbackInfoReturnable cir) {
        var entity = (Entity)(Object)this;
        if (ka.isEnabled() && entity == ka.getCurrentTarget())
            cir.setReturnValue(true);
    }

    @Inject(
        method = "Lnet/minecraft/entity/Entity;getTeamColorValue()I",
        at = @At("HEAD"), cancellable = true
    )
    public void getTeamColorValue(CallbackInfoReturnable cir) {
        Entity entity = (Entity)(Object)this;
        if (entity == ka.getCurrentTarget())
            cir.setReturnValue(new Color(127, 0, 0).getRGB());
    }
}
