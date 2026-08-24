package com.uris.crmhrm.client_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReactiveClientServiceTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ReactiveClientService reactiveClientService;

    @Test
    void emitsEveryClient() {
        given(clientService.findAll()).willReturn(List.of(
                new Client("Delta Logistika", "office@delta.rs", "100200300"),
                new Client("Nova Gradnja", "kontakt@novagradnja.rs", "100400500")));

        assertThat(reactiveClientService.findAll().collectList().block()).hasSize(2);
    }

    @Test
    void streamsEveryClient() {
        given(clientService.findAll()).willReturn(List.of(
                new Client("Delta Logistika", "office@delta.rs", "100200300"),
                new Client("Nova Gradnja", "kontakt@novagradnja.rs", "100400500")));

        assertThat(reactiveClientService.streamAll().collectList().block()).hasSize(2);
    }

    @Test
    void emitsClientFoundById() {
        given(clientService.findById(1L))
                .willReturn(Optional.of(new Client("Delta Logistika", "office@delta.rs", "100200300")));

        assertThat(reactiveClientService.findById(1L).block()).isNotNull();
    }

    @Test
    void emitsNothingForUnknownId() {
        given(clientService.findById(99L)).willReturn(Optional.empty());

        assertThat(reactiveClientService.findById(99L).blockOptional()).isEmpty();
    }

    @Test
    void emitsCreatedClient() {
        ClientRequest request = new ClientRequest("Panonija Soft", "info@panonijasoft.rs", "100600700");
        given(clientService.create(any(ClientRequest.class)))
                .willReturn(new Client("Panonija Soft", "info@panonijasoft.rs", "100600700"));

        assertThat(reactiveClientService.create(request).block()).isNotNull();
    }
}
