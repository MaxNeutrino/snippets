package io.github.maxneutrino.webfluxwebsockets.service;

import reactor.core.publisher.Flux;

public interface UnicastService {

    void onNext(String next);

    Flux<String> getMessages();
}
