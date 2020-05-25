package io.github.maxneutrino.igniteeventslistener.processor.noreflection;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteEvents;
import org.apache.ignite.events.Event;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgnitePredicate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NoReflectionCacheEventsListenerBeanPostProcessor implements BeanPostProcessor {

    private final Ignite ignite;

    @Autowired
    public NoReflectionCacheEventsListenerBeanPostProcessor(Ignite ignite) {
        this.ignite = ignite;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LocalIgniteEventsListener) {
            registerLocalListener((LocalIgniteEventsListener) bean);
        } else if (bean instanceof RemoteIgniteEventsListener) {
            registerRemoteListener((RemoteIgniteEventsListener) bean);
        }

        return bean;
    }

    private void registerLocalListener(LocalIgniteEventsListener bean) {
        IgniteEvents events = ignite.events();

        IgnitePredicate<Event> localListener = event -> {
            bean.onEvent(event);
            return true;
        };

        events.localListen(localListener, bean.events());
    }

    private void registerRemoteListener(RemoteIgniteEventsListener bean) {
        IgniteEvents events = ignite.events();

        IgniteBiPredicate<UUID, Event> remoteListener = (uuid, event) -> {
            bean.onEvent(uuid, event);
            return true;
        };

        events.remoteListen(remoteListener, event -> true, bean.events());
    }
}
