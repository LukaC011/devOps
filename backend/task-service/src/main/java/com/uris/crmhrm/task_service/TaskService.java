package com.uris.crmhrm.task_service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private static final String INITIAL_STATUS = "NEW";

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong();

    public List<Task> findAll() {
        return List.copyOf(tasks.values());
    }

    public Task create(TaskRequest request) {
        long id = idSequence.incrementAndGet();
        Task task = new Task(id, request.title(), request.description(), request.employeeId(),
                INITIAL_STATUS, Instant.now());
        tasks.put(id, task);
        return task;
    }
}
