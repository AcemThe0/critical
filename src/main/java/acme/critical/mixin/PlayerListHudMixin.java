package acme.critical.mixin;
import acme.critical.module.ModMan;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import acme.critical.module.client.Infohud;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
    @Inject(method = "getPlayerName", at = @At("HEAD"), cancellable = true)
    public void getPlayerName(PlayerListEntry playerListEntry, CallbackInfoReturnable<Text> cir) {
        Infohud infohud = ModMan.INSTANCE.getMod(Infohud.class);

        if (infohud.isEnabled() && infohud.doGamemodes.isEnabled()) cir.setReturnValue(infohud.displayGamemode(playerListEntry));
    }
}
