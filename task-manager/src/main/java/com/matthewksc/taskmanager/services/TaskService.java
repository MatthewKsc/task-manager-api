package com.matthewksc.taskmanager.services;

import com.matthewksc.taskmanager.dao.TaskListRepository;
import com.matthewksc.taskmanager.dao.TaskRepository;
import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private TaskListRepository taskListRepository;

    public TaskService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    public Task findById(Long Id) {
        return taskRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException(""));
    }

    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    public void deleteById(Long Id) {
        taskRepository.deleteById(Id);
    }

    public List<Task> getTaskByTaskList(Long taskListId){
        List<Task> tasks;
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(()-> new RuntimeException("Can't find task list with id: "+taskListId));
        tasks = taskRepository.findAllByTaskList(taskList);
        return tasks;
    }

    public Task save(Task task, Long taskListId) {
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(()-> new RuntimeException("Can't find task list with id: "+taskListId));
        task.setTaskList(taskList);
        taskRepository.save(task);

        return task;
    }
}
