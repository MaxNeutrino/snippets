package io.github.maxneutrino.igniteeventslistener.processor.noreflection;

import org.apache.ignite.events.Event;

public interface LocalIgniteEventsListener {

    void onEvent(Event cacheEvent);

    int[] events();
}
