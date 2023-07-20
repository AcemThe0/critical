package acme.critical.event.events;

import acme.critical.event.eventbus.Event;
import net.minecraft.client.util.math.MatrixStack;

public class EventWorldRender extends Event {

    protected float tickDelta;
    protected long limitTime;
    protected MatrixStack matrices;

    public EventWorldRender(float tickDelta, long limitTime, MatrixStack matrices) {
        this.tickDelta = tickDelta;
        this.limitTime = limitTime;
        this.matrices = matrices;
    }

    public static class Pre extends EventWorldRender {
        public Pre(float tickDelta, long limitTime, MatrixStack matrices) {
            super(tickDelta, limitTime, matrices);
        }
    }

    public static class Post extends EventWorldRender {
        public Post(float tickDelta, long limitTime, MatrixStack matrices) {
            super(tickDelta, limitTime, matrices);
        }
    }

    public float getDelta() {
        return tickDelta;
    }

    public long getLimitTime() {
        return limitTime;
    }

    public MatrixStack getMatrices() {
        return matrices;
    }
}
