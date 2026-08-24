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
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void returnsAllClients() {
        given(clientRepository.findAll())
                .willReturn(List.of(new Client("Delta Logistika", "office@delta.rs", "100200300")));

        assertThat(clientService.findAll()).hasSize(1);
    }

    @Test
    void findsClientById() {
        Client client = new Client("Delta Logistika", "office@delta.rs", "100200300");
        given(clientRepository.findById(1L)).willReturn(Optional.of(client));

        assertThat(clientService.findById(1L)).contains(client);
    }

    @Test
    void createsClientFromRequest() {
        given(clientRepository.save(any(Client.class))).willAnswer(call -> call.getArgument(0));

        Client created = clientService.create(
                new ClientRequest("Nova Gradnja", "kontakt@novagradnja.rs", "100400500"));

        assertThat(created.getName()).isEqualTo("Nova Gradnja");
        assertThat(created.getEmail()).isEqualTo("kontakt@novagradnja.rs");
        assertThat(created.getTaxNumber()).isEqualTo("100400500");
    }
}
