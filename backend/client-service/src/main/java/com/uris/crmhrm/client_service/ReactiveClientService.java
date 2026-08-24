package com.uris.crmhrm.client_service;

import java.time.Duration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ReactiveClientService {

    private static final Duration STREAM_INTERVAL = Duration.ofMillis(500);

    private final ClientService clientService;

    public ReactiveClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public Flux<Client> findAll() {
        return Mono.fromCallable(clientService::findAll)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<Client> streamAll() {
        return findAll().delayElements(STREAM_INTERVAL);
    }

    public Mono<Client> findById(Long id) {
        return Mono.fromCallable(() -> clientService.findById(id))
                .flatMap(Mono::justOrEmpty)
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Client> create(ClientRequest request) {
        return Mono.fromCallable(() -> clientService.create(request))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
