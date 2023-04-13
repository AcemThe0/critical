package acme.critical.event.eventbus;

import acme.critical.event.Event;
import org.apache.logging.log4j.Logger;
import acme.critical.event.eventbus.EventHandler;
import java.util.concurrent.atomic.AtomicLong;

public class CriticalEventBus {

    private final EventHandler handler;
    private final AtomicLong eventsPosted = new AtomicLong();

    private final Logger logger;

    public CriticalEventBus(EventHandler handler, Logger logger) {
        this.handler = handler;
        this.logger = logger;
    }

    public boolean subscribe(Object object) {
        return handler.subscribe(object);
    }

    public boolean unsubscribe(Object object) {
        return handler.unsubscribe(object);
    }

    public void post(Event event) {
        handler.post(event, logger);
        eventsPosted.getAndIncrement();
    }

    public long getEventsPosted() {
        return eventsPosted.get();
    }
}