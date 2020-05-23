package com.matthewksc.taskmanager.services;

import com.matthewksc.taskmanager.dao.Task;
import com.matthewksc.taskmanager.dao.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> findById(Long Id) {
        return taskRepository.findById(Id);
    }

    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    public void deleteById(Long Id) {
        taskRepository.deleteById(Id);
    }
}
