package com.matthewksc.taskmanager.controllers;

import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tasks/lists")
public class TaskListController {

    private TaskListService taskListService;

    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping
    public List<TaskList> getAllLists(){
        return (List<TaskList>) taskListService.findAll();
    }

    @GetMapping("/{Id}")
    public TaskList getListById(@PathVariable Long Id){
        return taskListService.findById(Id);
    }

    @PostMapping
    public TaskList saveTaskList(@RequestBody TaskList taskList){
        return taskListService.save(taskList);
    }

    @DeleteMapping("/{Id}")
    public void deleteListById(@PathVariable Long Id){
        taskListService.deleteById(Id);
    }
}
