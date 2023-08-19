package acme.critical.module.client;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventPacket;
import acme.critical.mixin.CustomPayloadC2SPacketAccessor;
import acme.critical.module.Mod;
import acme.critical.module.settings.StringSetting;

import io.netty.buffer.Unpooled;

public class Spoof extends Mod {
    private static StringSetting name =
        new StringSetting("Client brand", "Vanilla");

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
            CustomPayloadC2SPacketAccessor packet =
                (CustomPayloadC2SPacketAccessor)event.packet;
            Identifier id = packet.getChannel();
            if (id == CustomPayloadC2SPacket.BRAND)
                packet.setData(new PacketByteBuf(Unpooled.buffer())
                                   .writeString(name.getVal()));
        }
    }
}
