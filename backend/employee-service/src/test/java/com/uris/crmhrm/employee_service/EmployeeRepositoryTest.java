package com.uris.crmhrm.employee_service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void persistsAndReadsBackAnEmployee() {
        Employee saved = employeeRepository.save(new Employee("Ana", "Peric", "Accountant", "ana@crm.rs"));

        assertThat(saved.getId()).isNotNull();
        assertThat(employeeRepository.findById(saved.getId()))
                .get()
                .extracting(Employee::getEmail)
                .isEqualTo("ana@crm.rs");
    }
}
