package com.uris.crmhrm.client_service;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final List<Client> clients = List.of(
            new Client(1L, "Delta Logistika", "office@delta-logistika.rs", "100200300"),
            new Client(2L, "Nova Gradnja", "kontakt@novagradnja.rs", "100400500"),
            new Client(3L, "Panonija Soft", "info@panonijasoft.rs", "100600700"));

    public List<Client> findAll() {
        return clients;
    }
}
