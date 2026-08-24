package com.uris.crmhrm.task_service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void storesTaskWithGeneratedIdAndCreationTimestamp() {
        Task saved = taskRepository.save(new Task("Pozovi klijenta", "Provera statusa ugovora", 7L));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getStatus()).isEqualTo(TaskStatus.NEW);
        assertThat(taskRepository.findById(saved.getId()))
                .get()
                .extracting(Task::getEmployeeId)
                .isEqualTo(7L);
    }
}
