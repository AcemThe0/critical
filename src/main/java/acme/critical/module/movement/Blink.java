package acme.critical.module.movement;

import java.util.List;
import java.util.ArrayList;
import acme.critical.module.Mod;
import acme.critical.utils.FakePlayer;
import acme.critical.event.events.EventPacket;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Blink extends Mod {
    public List<PlayerMoveC2SPacket> packets = new ArrayList<>();
    FakePlayer fakeplayer;

    public Blink() {
        super("Blink", "Lagswitch.", Category.MOVEMENT);
        addSetting(new KeybindSetting("Key", 0));
    }

    @CriticalSubscribe
    public void sendPacket(EventPacket.Send event) {
        if (event.getPacket() instanceof PlayerMoveC2SPacket) {
            packets.add((PlayerMoveC2SPacket) event.getPacket());
            event.setCancelled(true);
            Critical.logger.info("Cancelled");
        }
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
        packets.clear();
        fakeplayer = new FakePlayer();
        fakeplayer.spawn();
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
        sendPackets();
        fakeplayer.despawn();
    }

    public void sendPackets() {
        for (PlayerMoveC2SPacket packet : new ArrayList<>(packets)) {
            if (!(packet instanceof PlayerMoveC2SPacket.LookAndOnGround)) {
                mc.player.networkHandler.sendPacket(packet);
            }
        }
        packets.clear();
    }
}
