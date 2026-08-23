package com.uris.crmhrm.employee_service;

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

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void returnsAllEmployees() throws Exception {
        given(employeeService.findAll())
                .willReturn(List.of(new Employee("Mila", "Jovanovic", "HR Manager", "mila@crm.rs")));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Mila"));
    }

    @Test
    void createsEmployee() throws Exception {
        given(employeeService.create(any()))
                .willReturn(new Employee("Petar", "Ilic", "Developer", "petar@crm.rs"));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Petar\",\"lastName\":\"Ilic\",\"position\":\"Developer\",\"email\":\"petar@crm.rs\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.position").value("Developer"));
    }

    @Test
    void returnsNotFoundForUnknownEmployee() throws Exception {
        given(employeeService.findById(eq(99L))).willReturn(Optional.empty());

        mockMvc.perform(get("/employees/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsNotFoundWhenDeletingUnknownEmployee() throws Exception {
        given(employeeService.deleteById(eq(99L))).willReturn(false);

        mockMvc.perform(delete("/employees/99"))
                .andExpect(status().isNotFound());
    }
}
