package acme.critical.event.events;

import acme.critical.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class EventWorldRender extends Event {

    protected float partialTicks;
    protected MatrixStack matrices;

    public static class Pre extends EventWorldRender {

        public Pre(float partialTicks, MatrixStack matrices) {
            this.partialTicks = partialTicks;
            this.matrices = matrices;
        }
    
    }

    public static class Post extends EventWorldRender {

        public Post(float partialTicks, MatrixStack matrices) {
            this.partialTicks = partialTicks;
            this.matrices = matrices;
        }

    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public MatrixStack getMatrices() {
        return matrices;
    }
}