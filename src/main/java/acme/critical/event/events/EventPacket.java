package acme.critical.event.events;

import acme.critical.event.Event;
import net.minecraft.network.Packet;


public class EventPacket extends Event {

    private Packet<?> packet;

    public EventPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public static class Read extends EventPacket {

        public Read(Packet<?> packet) {
            super(packet);
        }

    }

    public static class Send extends EventPacket {

        public Send(Packet<?> packet) {
            super(packet);
        }

    }
}
