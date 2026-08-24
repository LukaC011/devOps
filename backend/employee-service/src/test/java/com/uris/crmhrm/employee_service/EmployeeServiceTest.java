package com.uris.crmhrm.employee_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void returnsAllEmployees() {
        given(employeeRepository.findAll())
                .willReturn(List.of(new Employee("Mila", "Jovanovic", "HR Manager", "mila@crm.rs")));

        assertThat(employeeService.findAll()).hasSize(1);
    }

    @Test
    void findsEmployeeById() {
        Employee employee = new Employee("Mila", "Jovanovic", "HR Manager", "mila@crm.rs");
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        assertThat(employeeService.findById(1L)).contains(employee);
    }

    @Test
    void createsEmployeeFromRequest() {
        given(employeeRepository.save(any(Employee.class))).willAnswer(call -> call.getArgument(0));

        Employee created = employeeService.create(
                new EmployeeRequest("Petar", "Ilic", "Developer", "petar@crm.rs"));

        assertThat(created.getFirstName()).isEqualTo("Petar");
        assertThat(created.getLastName()).isEqualTo("Ilic");
        assertThat(created.getPosition()).isEqualTo("Developer");
        assertThat(created.getEmail()).isEqualTo("petar@crm.rs");
    }

    @Test
    void updatesExistingEmployee() {
        Employee existing = new Employee("Petar", "Ilic", "Developer", "petar@crm.rs");
        given(employeeRepository.findById(1L)).willReturn(Optional.of(existing));
        given(employeeRepository.save(any(Employee.class))).willAnswer(call -> call.getArgument(0));

        Optional<Employee> updated = employeeService.update(1L,
                new EmployeeRequest("Petar", "Ilic", "Senior Developer", "petar.ilic@crm.rs"));

        assertThat(updated).isPresent();
        assertThat(updated.get().getPosition()).isEqualTo("Senior Developer");
        assertThat(updated.get().getEmail()).isEqualTo("petar.ilic@crm.rs");
    }

    @Test
    void doesNotUpdateUnknownEmployee() {
        given(employeeRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(employeeService.update(99L,
                new EmployeeRequest("X", "Y", "Z", "x@crm.rs"))).isEmpty();
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deletesExistingEmployee() {
        given(employeeRepository.existsById(1L)).willReturn(true);

        assertThat(employeeService.deleteById(1L)).isTrue();
        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void doesNotDeleteUnknownEmployee() {
        given(employeeRepository.existsById(99L)).willReturn(false);

        assertThat(employeeService.deleteById(99L)).isFalse();
        verify(employeeRepository, never()).deleteById(99L);
    }
}
