package acme.critical.mixin;

import net.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
    // causes game 2 crash on lclick
    //@Invoker("doAttack")
    // boolean doAttack();
}
