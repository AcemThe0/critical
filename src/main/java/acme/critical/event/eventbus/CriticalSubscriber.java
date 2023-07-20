package acme.critical.event.eventbus;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.function.Consumer;

import org.apache.commons.lang3.reflect.MethodUtils;

@SuppressWarnings("unchecked")
public class CriticalSubscriber {

    private final Consumer<Object> subscriberCaller;
    private final Class<? extends Event> eventClass;
    private final Class<?> targetClass;
    private final String signature;

    public CriticalSubscriber(
        Object target, String methodName, Class<? extends Event> eventClass
    ) {
        this(
            target,
            MethodUtils.getAccessibleMethod(
                target.getClass(), methodName, eventClass
            ),
            eventClass
        );
    }

    public CriticalSubscriber(Object target, Method method) {
        this(target, method, getEvent(method));
    }

    public CriticalSubscriber(
        Object target, Method method, Class<? extends Event> eventClass
    ) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            CallSite callsite = LambdaMetafactory.metafactory(
                lookup, "accept",
                MethodType.methodType(Consumer.class, target.getClass()),
                MethodType.methodType(void.class, Object.class),
                lookup.unreflect(method),
                MethodType.methodType(void.class, eventClass)
            );

            subscriberCaller =
                (Consumer<Object>)callsite.getTarget().invokeWithArguments(
                    target
                );
            this.eventClass = eventClass;
            this.targetClass = target.getClass();
            this.signature = targetClass.getName() + "." + method.getName() +
                             "(" + eventClass.getName() + ")";
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static Class<? extends Event> getEvent(Method method) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 0 ||
            !Event.class.isAssignableFrom(parameters[0].getType())) {
            throw new RuntimeException(
                "Tried to create Subscriber. Invalid parameters"
            );
        }

        return (Class<? extends Event>)parameters[0].getType();
    }

    public void callSubscriber(Event event) { subscriberCaller.accept(event); }

    public Class<? extends Event> getEventClass() { return eventClass; }

    public Class<?> getTargetClass() { return targetClass; }

    public String getSignature() { return signature; }

    @Override
    public final int hashCode() {
        return signature.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof CriticalSubscriber) {
            CriticalSubscriber that = (CriticalSubscriber)obj;
            return signature.equals(that.signature);
        }

        return false;
    }
}
