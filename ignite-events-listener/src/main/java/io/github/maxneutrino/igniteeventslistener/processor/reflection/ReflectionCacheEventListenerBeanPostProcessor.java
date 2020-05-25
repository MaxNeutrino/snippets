package io.github.maxneutrino.igniteeventslistener.processor.reflection;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteEvents;
import org.apache.ignite.events.Event;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgnitePredicate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Component
public class ReflectionCacheEventListenerBeanPostProcessor implements BeanPostProcessor {

    private final Ignite ignite;

    @Autowired
    public ReflectionCacheEventListenerBeanPostProcessor(Ignite igntie) {
        this.ignite = igntie;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(CacheEventsListener.class)) {

                Class<?>[] parameters = method.getParameterTypes();

                if (parameters.length < 1 || parameters.length > 2) {
                    throw new UnsupportedOperationException("Expected 1 or 2 parameters, but was found " + parameters.length);
                }

                boolean isEventClassPresent = false;
                boolean isUUIDClassPresent = false;
                int eventTypeIndex = 0;

                for (int i = 0; i < parameters.length; i++) {
                    if (Event.class.isAssignableFrom(parameters[i])) {
                        eventTypeIndex = i;
                        isEventClassPresent = true;
                    } else if (UUID.class.isAssignableFrom(parameters[i])) {
                        isUUIDClassPresent = true;
                    } else {
                        throw new UnsupportedOperationException("Supported types is: " + Event.class.getName() + " and "
                                + UUID.class.getName());
                    }
                }

                if (!isEventClassPresent)
                    throw new UnsupportedOperationException("Expected parameter with type " + Event.class.getName());

                if (isUUIDClassPresent) {
                    registerRemoteListener(bean, method, eventTypeIndex);
                } else {
                    registerLocalListener(bean, method);
                }
            }
        }

        return bean;
    }

    private void registerLocalListener(Object bean, Method method) {
        IgniteEvents igniteEvents = ignite.events();

        IgnitePredicate<Event> localListener = event -> {
            try {
                method.invoke(bean, event);
            } catch (Exception e) {
                // TODO handle exceptions in a nice way
            }
            return true;
        };

        int[] events = method.getAnnotation(CacheEventsListener.class).value();
        igniteEvents.localListen(localListener, events);
    }

    private void registerRemoteListener(Object bean, Method method, int eventTypeIndex) {
        IgniteEvents igniteEvents = ignite.events();

        IgniteBiPredicate<UUID, Event> remoteListener = (uuid, event) -> {
            try {
                if (eventTypeIndex == 0)
                    method.invoke(bean, event, uuid);
                else
                    method.invoke(bean, uuid, event);
            } catch (Exception e) {
                // TODO handle exceptions in a nice way
            }
            return true;
        };

        int[] events = method.getAnnotation(CacheEventsListener.class).value();
        igniteEvents.remoteListen(remoteListener, event -> true, events);
    }
}
