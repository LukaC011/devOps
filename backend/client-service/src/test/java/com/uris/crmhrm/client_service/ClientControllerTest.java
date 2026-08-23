package com.uris.crmhrm.client_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ReactiveClientService reactiveClientService;

    @Test
    void returnsAllClients() {
        given(reactiveClientService.findAll())
                .willReturn(Flux.just(new Client("Delta Logistika", "office@delta-logistika.rs", "100200300")));

        webTestClient.get().uri("/clients")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Client.class).hasSize(1);
    }

    @Test
    void createsClient() {
        given(reactiveClientService.create(any()))
                .willReturn(Mono.just(new Client("Nova Gradnja", "kontakt@novagradnja.rs", "100400500")));

        webTestClient.post().uri("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":\"Nova Gradnja\",\"email\":\"kontakt@novagradnja.rs\",\"taxNumber\":\"100400500\"}")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.taxNumber").isEqualTo("100400500");
    }

    @Test
    void returnsNotFoundForUnknownClient() {
        given(reactiveClientService.findById(eq(99L))).willReturn(Mono.empty());

        webTestClient.get().uri("/clients/99")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void streamsClientsAsServerSentEvents() {
        given(reactiveClientService.streamAll())
                .willReturn(Flux.just(new Client("Panonija Soft", "info@panonijasoft.rs", "100600700")));

        webTestClient.get().uri("/clients/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM);
    }

    @Test
    void streamsClientsThroughRxJavaFlowable() {
        given(reactiveClientService.streamAll())
                .willReturn(Flux.just(new Client("Panonija Soft", "info@panonijasoft.rs", "100600700")));

        webTestClient.get().uri("/clients/flowable")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM);
    }
}
