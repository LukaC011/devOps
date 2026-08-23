package com.uris.crmhrm.client_service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void persistsAndReadsBackAClient() {
        Client saved = clientRepository.save(new Client("Panonija Soft", "info@panonijasoft.rs", "100600700"));

        assertThat(saved.getId()).isNotNull();
        assertThat(clientRepository.findById(saved.getId()))
                .get()
                .extracting(Client::getTaxNumber)
                .isEqualTo("100600700");
    }
}
