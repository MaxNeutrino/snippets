package io.github.maxneutrino.igniteeventslistener.processor.noreflection;

import org.apache.ignite.events.Event;

import java.util.UUID;

public interface RemoteIgniteEventsListener {

    void onEvent(UUID uuid, Event cacheEvent);

    int[] events();
}
