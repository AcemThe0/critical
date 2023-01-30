package acme.critical.module.client;

import acme.critical.Critical;
import acme.critical.module.Mod;
import io.netty.buffer.Unpooled;
import net.minecraft.util.Identifier;
import net.minecraft.network.PacketByteBuf;
import acme.critical.event.events.EventPacket;
//import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.StringSetting;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.mixin.CustomPayloadC2SPacketAccessor;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

public class Spoof extends Mod {
    //private static ModeSetting name = new ModeSetting("Name", "Vanilla", "Vanilla", "Jungle", "fourchan");
    private static StringSetting name = new StringSetting("Name", "Vanilla");

    public Spoof() {
        super("Spoof", "Spoofs client brand.", Category.CLIENT);
        addSetting(name);
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
    }

    @CriticalSubscribe
    public void sendPacket(EventPacket.Send event) {
        if (event.packet instanceof CustomPayloadC2SPacket) {
            CustomPayloadC2SPacketAccessor packet = (CustomPayloadC2SPacketAccessor) event.packet;
            Identifier id = packet.getChannel();
            //if (id == CustomPayloadC2SPacket.BRAND) packet.setData(new PacketByteBuf(Unpooled.buffer()).writeString(name.getMode()));
            if (id == CustomPayloadC2SPacket.BRAND) packet.setData(new PacketByteBuf(Unpooled.buffer()).writeString(name.getVal()));

        }
    }

}
