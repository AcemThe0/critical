package acme.critical.event.events;

import net.minecraft.network.packet.Packet;

import acme.critical.event.eventbus.Event;

public class EventPacket extends Event {

    public Packet<?> packet;

    public EventPacket(Packet<?> packet) { this.packet = packet; }

    public Packet<?> getPacket() { return packet; }

    public void setPacket(Packet<?> packet) { this.packet = packet; }

    public static class Read extends EventPacket {

        public Read(Packet<?> packet) { super(packet); }
    }

    public static class Send extends EventPacket {

        public Send(Packet<?> packet) { super(packet); }
    }
}
