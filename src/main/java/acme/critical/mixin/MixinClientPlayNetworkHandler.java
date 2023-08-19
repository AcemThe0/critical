package acme.critical.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.message.SignedMessage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    // Workaround chat reports by pretending everyone is blocked. This is a hack
    // and will likely get patched (I hope that is not the case though).
    // Kudos to Nodus' "Guardian"

    @ModifyVariable(
        method = "acknowledge", at = @At("HEAD"), argsOnly = true, index = 2
    )
    public boolean
    acknowledge(boolean value, SignedMessage message) {
        return message.getSender().equals(
            MinecraftClient.getInstance().player.getUuid()
        );
    }
}