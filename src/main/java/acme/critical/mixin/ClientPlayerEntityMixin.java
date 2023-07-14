package acme.critical.mixin;

import acme.critical.Critical;
import net.minecraft.util.math.Vec3d;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.MovementType;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.gen.Accessor;
import acme.critical.event.events.EventClientMove;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.injection.Redirect;

import acme.critical.module.ModMan;
import acme.critical.module.visual.Freecam;
import acme.critical.module.visual.Norender;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow private void autoJump(float dx, float dz) {}
    
    private ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void move(MovementType type, Vec3d movement, CallbackInfo ci) {
        EventClientMove event = new EventClientMove(type, movement);
        Critical.INSTANCE.eventBus.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        } else if (!type.equals(event.getType()) || !movement.equals(event.getVec())) {
            double double_1 = this.getX();
            double double_2 = this.getZ();
            super.move(event.getType(), event.getVec());
            this.autoJump((float) (this.getX() - double_1), (float) (this.getZ() - double_2));
            ci.cancel();
        }
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void pushOutOfBlocks(double x, double d, CallbackInfo ci) {
        Freecam freecam = ModMan.INSTANCE.getMod(Freecam.class);
        if (freecam.isEnabled()) {
            ci.cancel();
        }
    }

    @Redirect(method = "updateNausea", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;"))
    private Screen onUpdateNauseaScreen(MinecraftClient client) {
	Norender norender = ModMan.INSTANCE.getMod(Norender.class);
	if (norender.isEnabled() && norender.portals.isEnabled()) {
		return null;
	} else {
		return client.currentScreen;
	}
    }

    @Inject(method = "updateNausea", at = @At("HEAD"), cancellable = true)
    public void onUpdateNausea(CallbackInfo ci) {
        Norender norender = ModMan.INSTANCE.getMod(Norender.class);
        if (norender.isEnabled() && norender.nausea.isEnabled())
            ci.cancel();
    }
}
