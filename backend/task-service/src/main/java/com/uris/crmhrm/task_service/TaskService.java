package com.uris.crmhrm.task_service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public Task create(TaskRequest request) {
        return taskRepository.save(new Task(request.title(), request.description(), request.employeeId()));
    }

    @Transactional
    public Optional<Task> update(Long id, TaskUpdateRequest request) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(request.title());
            task.setDescription(request.description());
            task.setEmployeeId(request.employeeId());
            task.setStatus(request.status());
            return taskRepository.save(task);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }
}
