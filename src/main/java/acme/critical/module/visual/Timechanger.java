package acme.critical.module.visual;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventPacket;
import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;

public class Timechanger extends Mod {
    private NumberSetting time = new NumberSetting("Time", 0, 24, 13, 1);

    private MinecraftClient mc = MinecraftClient.getInstance();

    long serverTime;

    public Timechanger() {
        super(
            "TimeChanger", "Change the time of day clientside.", Category.VISUAL
        );
        addSettings(time, new KeybindSetting("Key", 0));
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
        serverTime = mc.world.getTime();
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
        mc.world.setTimeOfDay(serverTime);
    }

    @Override
    public void onTick() {
        mc.world.setTimeOfDay(time.getValueInt() * 1000);
    }

    @CriticalSubscribe
    public void recievePacket(EventPacket.Read event) {
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket) {
            serverTime = ((WorldTimeUpdateS2CPacket)event.packet).getTime();
            event.setCancelled(true);
        }
    }
}
