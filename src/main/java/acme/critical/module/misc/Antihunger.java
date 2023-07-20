package acme.critical.module.misc;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventPacket;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Antihunger extends Mod {
    public Antihunger() {
        super("Antihunger", "Tries to use less food.", Category.MISC);
        addSettings(new KeybindSetting("Key", 0));
    }

    @CriticalSubscribe
    public void sendPacket(EventPacket.Send event) {
        if (event.getPacket() instanceof PlayerMoveC2SPacket) {
            if (mc.player.getVelocity().y != 0 &&
                !mc.options.jumpKey.isPressed()) {
                if (((PlayerMoveC2SPacket)event.getPacket()).isOnGround()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
    }
}
