package acme.critical.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
	@Invoker("doAttack")
	boolean doAttack();
}
