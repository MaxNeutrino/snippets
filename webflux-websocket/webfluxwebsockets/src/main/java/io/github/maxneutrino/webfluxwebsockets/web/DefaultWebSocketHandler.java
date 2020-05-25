package io.github.maxneutrino.webfluxwebsockets.web;

import io.github.maxneutrino.webfluxwebsockets.service.UnicastService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class DefaultWebSocketHandler implements WebSocketHandler {

    private UnicastService unicastService;

    public DefaultWebSocketHandler(UnicastService unicastService) {
        this.unicastService = unicastService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> messages = session.receive().flatMap(message -> unicastService.getMessages())
                .map(session::textMessage);

        return session.send(messages);
    }
}
