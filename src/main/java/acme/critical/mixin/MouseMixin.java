package acme.critical.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.Mouse;

import acme.critical.Critical;
import acme.critical.module.misc.Friend;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    public void onMouseButton(
        long window, int button, int action, int modifiers, CallbackInfo ci
    ) {
        if (Friend.friendEnabled && button == 2 &&
            action == 1) { // action 1 == click
            Friend.middleClickFriend();
        }
    }
}
