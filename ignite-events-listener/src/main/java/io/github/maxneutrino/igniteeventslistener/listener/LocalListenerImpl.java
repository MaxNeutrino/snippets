package io.github.maxneutrino.igniteeventslistener.listener;

import io.github.maxneutrino.igniteeventslistener.processor.noreflection.LocalIgniteEventsListener;
import org.apache.ignite.events.Event;
import org.apache.ignite.events.EventType;
import org.springframework.stereotype.Component;

@Component
public class LocalListenerImpl implements LocalIgniteEventsListener {
    @Override
    public void onEvent(Event cacheEvent) {
        System.out.println(cacheEvent);
    }

    @Override
    public int[] events() {
        return new int[]{EventType.EVT_CACHE_ENTRY_CREATED, EventType.EVT_CACHE_ENTRY_DESTROYED};
    }
}
