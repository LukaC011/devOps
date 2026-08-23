package com.uris.crmhrm.task_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void returnsAllTasks() throws Exception {
        given(taskService.findAll())
                .willReturn(List.of(new Task("Pripremi ponudu", "Ponuda za Delta Logistiku", 1L)));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].status").value("NEW"));
    }

    @Test
    void createsTaskWithNewStatus() throws Exception {
        given(taskService.create(any()))
                .willReturn(new Task("Pripremi ponudu", "Ponuda za Delta Logistiku", 1L));

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Pripremi ponudu\",\"description\":\"Ponuda za Delta Logistiku\",\"employeeId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Pripremi ponudu"))
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    @Test
    void returnsNotFoundForUnknownTask() throws Exception {
        given(taskService.findById(eq(99L))).willReturn(Optional.empty());

        mockMvc.perform(get("/tasks/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsNotFoundWhenDeletingUnknownTask() throws Exception {
        given(taskService.deleteById(eq(99L))).willReturn(false);

        mockMvc.perform(delete("/tasks/99"))
                .andExpect(status().isNotFound());
    }
}
