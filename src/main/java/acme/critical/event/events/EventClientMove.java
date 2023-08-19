package acme.critical.event.events;

import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

import acme.critical.event.eventbus.Event;

public class EventClientMove extends Event {
    private MovementType type;
    private Vec3d vec;

    public EventClientMove(MovementType type, Vec3d vec) {
        this.type = type;
        this.vec = vec;
    }

    public MovementType getType() { return type; }

    public void setType(MovementType type) { this.type = type; }

    public Vec3d getVec() { return vec; }

    public void setVec(Vec3d vec) { this.vec = vec; }
}
