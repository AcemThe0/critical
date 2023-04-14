package acme.critical.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;

import acme.critical.Critical;
import acme.critical.event.events.EventTick;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void onTick(CallbackInfo ci) {
		Critical.eventBus.post(new EventTick());

		Critical.INSTANCE.onTick();
	}
}
