package com.matthewksc.taskmanager.services;

import com.matthewksc.taskmanager.dao.TaskList;
import com.matthewksc.taskmanager.dao.TaskListRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskListService {

    private TaskListRepository taskListRepository;

    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    public TaskList save(TaskList taskList) {
        taskListRepository.save(taskList);
        return taskList;
    }

    public TaskList findById(Long Id) {
        return taskListRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("No TaskList with id: "+ Id));
    }

    public Iterable<TaskList> findAll() {
        return taskListRepository.findAll();
    }

    public void deleteById(Long aLong) {
        taskListRepository.deleteById(aLong);
    }
}
