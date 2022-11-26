package acme.critical.event.eventbus;

import java.util.Map;
import java.util.List;
import java.lang.reflect.Method;
import acme.critical.event.Event;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.eventbus.CriticalSubscriber;


public class InexactEventHandler extends EventHandler {

    private final Map<Class<?>, List<CriticalSubscriber>> subscribers = new ConcurrentHashMap<>();

    public InexactEventHandler(String id) {
        super(id);
    }

    public boolean subscribe(Object object) {
        boolean added = false;
        for (Method m: object.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(CriticalSubscribe.class) && m.getParameters().length != 0) {
                subscribers.computeIfAbsent(m.getParameters()[0].getType(), k -> new CopyOnWriteArrayList<>()).add(new CriticalSubscriber(object, m));
                added = true;
            }
        }

        return added;
    }

    public boolean unsubscribe(Object object) {
        boolean[] removed = new boolean[1];
        subscribers.values().removeIf(v -> {
            removed[0] |= v.removeIf(s -> object.getClass().equals(s.getTargetClass()));
            return v.isEmpty();
        });

        return removed[0];
    }

    public void post(Event event, Logger logger) {
        for (Map.Entry<Class<?>, List<CriticalSubscriber>> entry: subscribers.entrySet()) {
            if (entry.getKey().isAssignableFrom(event.getClass())) {
                for (CriticalSubscriber s: entry.getValue()) {
                    try {
                        s.callSubscriber(event);
                    } catch (Throwable t) {
                        logger.error("Exception thrown by subscriber method " + s.getSignature() + " when dispatching event: " + s.getEventClass().getName(), t);
                    }
                }
            }
        }
    }
}
