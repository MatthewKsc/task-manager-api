package com.matthewksc.taskmanager.controllers;

import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
    public Task getTaskById(@PathVariable Long Id){
        return taskService.findById(Id);
    }

    @GetMapping("/byTaskList/{taskListId}")
    public List<Task> getTaskByTaskList(@PathVariable Long taskListId){
        return taskService.getTaskByTaskList(taskListId);
    }

    @PostMapping("/{taskListId}")
    public Task saveTask(@RequestBody Task task, @PathVariable Long taskListId){
        taskService.save(task, taskListId);
        return task;
    }

    @DeleteMapping("/{Id}")
    public void deleteTaskById(@PathVariable Long Id){
        taskService.deleteById(Id);
    }

}
