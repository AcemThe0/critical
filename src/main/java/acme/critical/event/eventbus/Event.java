package acme.critical.event.eventbus;

public class Event {
    private boolean cancelled = false;

    public boolean isCancelled() { return cancelled; }

    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
}
