package com.uris.crmhrm.client_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Test
    void returnsAllClients() throws Exception {
        given(clientService.findAll())
                .willReturn(List.of(new Client("Delta Logistika", "office@delta-logistika.rs", "100200300")));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Delta Logistika"));
    }

    @Test
    void createsClient() throws Exception {
        given(clientService.create(any()))
                .willReturn(new Client("Nova Gradnja", "kontakt@novagradnja.rs", "100400500"));

        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Nova Gradnja\",\"email\":\"kontakt@novagradnja.rs\",\"taxNumber\":\"100400500\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.taxNumber").value("100400500"));
    }

    @Test
    void returnsNotFoundForUnknownClient() throws Exception {
        given(clientService.findById(eq(99L))).willReturn(Optional.empty());

        mockMvc.perform(get("/clients/99"))
                .andExpect(status().isNotFound());
    }
}
