package io.github.maxneutrino.webfluxwebsockets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EventGenerator {

    private AtomicInteger counter = new AtomicInteger(0);

    private UnicastService unicastService;

    @Autowired
    public EventGenerator(UnicastService unicastService) {
        this.unicastService = unicastService;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void generateEvent() {
        int count = counter.getAndIncrement();
        unicastService.onNext("Event #" + count);
    }
}
