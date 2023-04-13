package acme.critical.module.movement;

import java.util.List;
import java.util.ArrayList;
import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.utils.FakePlayer;
import acme.critical.event.events.EventPacket;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.event.eventbus.CriticalSubscribe;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Blink extends Mod {
    private NumberSetting tickPause = new NumberSetting("Interrupt", 0.0, 100, 0, 1);
    private BooleanSetting showPlayer = new BooleanSetting("ShowPlayer", true);
    public List<PlayerMoveC2SPacket> packets = new ArrayList<>();
    FakePlayer fakeplayer;
    int ticksSkipped = 0;

    public Blink() {
        super("Blink", "Lagswitch.", Category.MOVEMENT);
        addSettings(showPlayer, tickPause, new KeybindSetting("Key", 0));
    }

    @CriticalSubscribe
    public void sendPacket(EventPacket.Send event) {
        if (event.getPacket() instanceof PlayerMoveC2SPacket) {
            packets.add((PlayerMoveC2SPacket) event.getPacket());
            event.setCancelled(true);

            if(tickPause.getValueInt() != 0) ticksSkipped+=1;
            if (tickPause.getValueInt() != 0 && ticksSkipped >= tickPause.getValueInt()) {
                refresh();
                ticksSkipped = 0;
            }
        }
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
        packets.clear();
        fakeplayer = new FakePlayer();
        if (showPlayer.isEnabled()) fakeplayer.spawn();
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
        sendPackets();
        fakeplayer.despawn();
    }

    private void refresh() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
        sendPackets();
        fakeplayer.despawn();
        ticksSkipped = 0;

        Critical.INSTANCE.eventBus.subscribe(this);
        fakeplayer = new FakePlayer();
        if (showPlayer.isEnabled()) fakeplayer.spawn();
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
