package io.github.maxneutrino.igniteeventslistener.listener;

import io.github.maxneutrino.igniteeventslistener.processor.reflection.CacheEventsListener;
import org.apache.ignite.events.Event;
import org.apache.ignite.events.EventType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AnnotatedListener {

    @CacheEventsListener({EventType.EVT_CACHE_ENTRY_CREATED, EventType.EVT_CACHE_ENTRY_DESTROYED})
    public void onLocalEvent(Event cacheEvent) {
        System.out.println(cacheEvent);
    }

    @CacheEventsListener({EventType.EVT_CACHE_ENTRY_CREATED, EventType.EVT_CACHE_ENTRY_DESTROYED})
    public void onRemoteEvent(UUID uuid, Event cacheEvent) {
        System.out.println(uuid + " " + cacheEvent);
    }
}
