package com.matthewksc.taskmanager.services;

import com.matthewksc.taskmanager.dao.Task;
import com.matthewksc.taskmanager.dao.TaskList;
import com.matthewksc.taskmanager.dao.TaskListRepository;
import com.matthewksc.taskmanager.dao.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private TaskListRepository taskListRepository;

    public TaskService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> findById(Long Id) {
        return taskRepository.findById(Id);
    }

    public List<Task> getTaskByTaskList(Long taskListId){
        List<Task> tasks;
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(()-> new RuntimeException("Can't find task list with id: "+taskListId));
        tasks = taskRepository.findAllByTaskList(taskList);
        return tasks;
    }

    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    public void deleteById(Long Id) {
        taskRepository.deleteById(Id);
    }
}
