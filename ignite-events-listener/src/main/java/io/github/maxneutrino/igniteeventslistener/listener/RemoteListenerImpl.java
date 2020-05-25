package io.github.maxneutrino.igniteeventslistener.listener;

import io.github.maxneutrino.igniteeventslistener.processor.noreflection.RemoteIgniteEventsListener;
import org.apache.ignite.events.Event;
import org.apache.ignite.events.EventType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RemoteListenerImpl implements RemoteIgniteEventsListener {

    @Override
    public void onEvent(UUID uuid, Event cacheEvent) {
        System.out.println(uuid + " " + cacheEvent);
    }

    @Override
    public int[] events() {
        return new int[]{EventType.EVT_CACHE_ENTRY_CREATED, EventType.EVT_CACHE_ENTRY_DESTROYED};
    }
}
