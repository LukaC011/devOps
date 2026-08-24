package com.uris.crmhrm.client_service;

import io.reactivex.rxjava3.core.Flowable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ReactiveClientService reactiveClientService;

    public ClientController(ReactiveClientService reactiveClientService) {
        this.reactiveClientService = reactiveClientService;
    }

    @GetMapping
    public Flux<Client> getClients() {
        return reactiveClientService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Client>> getClient(@PathVariable Long id) {
        return reactiveClientService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Client> createClient(@RequestBody ClientRequest request) {
        return reactiveClientService.create(request);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Client> streamClients() {
        return reactiveClientService.streamAll();
    }

    @GetMapping(value = "/flowable", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flowable<Client> streamClientsAsFlowable() {
        return Flowable.fromPublisher(reactiveClientService.streamAll());
    }
}
