package com.matthewksc.taskmanager.controllers;

import com.matthewksc.taskmanager.dao.Task;
import com.matthewksc.taskmanager.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/task")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public Iterable<Task> getAllTasks(){
        return taskService.findAll();
    }

    @GetMapping("/{Id}")
    public Optional<Task> getTaskById(@PathVariable Long Id){
        return taskService.findById(Id);
    }

    @GetMapping("/byTaskList/{taskListId}")
    public List<Task> getTaskByTaskList(@PathVariable Long taskListId){
        return taskService.getTaskByTaskList(taskListId);
    }

    @PostMapping
    public Task saveTask(@RequestBody Task task){
        taskService.save(task);
        return task;
    }

    @DeleteMapping("/{Id}")
    public void deleteTaskById(@PathVariable Long Id){
        taskService.deleteById(Id);
    }

}
