package acme.critical.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;

import acme.critical.Critical;
import acme.critical.event.events.EventKeyboard;
import acme.critical.ui.screens.clickgui.ClickGUI;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(
        long window, int key, int scancode, int action, int modifiers,
        CallbackInfo ci
    ) {
        if (MinecraftClient.getInstance().currentScreen instanceof ClickGUI)
            Critical.eventBus.post(
                new EventKeyboard.Cgui(key, action, modifiers)
            );
        else
            Critical.eventBus.post(new EventKeyboard(key, action, modifiers));
    }
}
