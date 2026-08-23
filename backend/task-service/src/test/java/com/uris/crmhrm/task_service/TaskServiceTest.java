package com.uris.crmhrm.task_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmployeeClient employeeClient;

    @InjectMocks
    private TaskService taskService;

    @Test
    void returnsAllTasks() {
        given(taskRepository.findAll())
                .willReturn(List.of(new Task("Pripremi ponudu", "Ponuda za Delta", 1L)));

        assertThat(taskService.findAll()).hasSize(1);
    }

    @Test
    void findsTaskById() {
        Task task = new Task("Pripremi ponudu", "Ponuda za Delta", 1L);
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        assertThat(taskService.findById(1L)).contains(task);
    }

    @Test
    void savesTaskWhenEmployeeExists() {
        TaskRequest request = new TaskRequest("Pripremi ponudu", "Ponuda za Delta", 1L);
        given(employeeClient.employeeExists(1L)).willReturn(true);
        given(taskRepository.save(any(Task.class))).willAnswer(invocation -> invocation.getArgument(0));

        Task created = taskService.create(request);

        assertThat(created.getEmployeeId()).isEqualTo(1L);
        assertThat(created.getStatus()).isEqualTo(TaskStatus.NEW);
        assertThat(created.getCreatedAt()).isNotNull();
        assertThat(created.getDescription()).isEqualTo("Ponuda za Delta");
    }

    @Test
    void rejectsTaskForUnknownEmployee() {
        TaskRequest request = new TaskRequest("Pripremi ponudu", "Ponuda za Delta", 99L);
        given(employeeClient.employeeExists(99L)).willReturn(false);

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(UnknownEmployeeException.class);

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updatesExistingTask() {
        Task existing = new Task("Pripremi ponudu", "Ponuda za Delta", 1L);
        given(taskRepository.findById(1L)).willReturn(Optional.of(existing));
        given(taskRepository.save(any(Task.class))).willAnswer(invocation -> invocation.getArgument(0));

        Optional<Task> updated = taskService.update(1L,
                new TaskUpdateRequest("Zavrsi ponudu", "Finalna verzija", 2L, TaskStatus.IN_PROGRESS));

        assertThat(updated).isPresent();
        assertThat(updated.get().getTitle()).isEqualTo("Zavrsi ponudu");
        assertThat(updated.get().getDescription()).isEqualTo("Finalna verzija");
        assertThat(updated.get().getEmployeeId()).isEqualTo(2L);
        assertThat(updated.get().getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }

    @Test
    void doesNotUpdateUnknownTask() {
        given(taskRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(taskService.update(99L,
                new TaskUpdateRequest("x", "y", 1L, TaskStatus.DONE))).isEmpty();
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deletesExistingTask() {
        given(taskRepository.existsById(1L)).willReturn(true);

        assertThat(taskService.deleteById(1L)).isTrue();
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void doesNotDeleteUnknownTask() {
        given(taskRepository.existsById(99L)).willReturn(false);

        assertThat(taskService.deleteById(99L)).isFalse();
        verify(taskRepository, never()).deleteById(99L);
    }
}
