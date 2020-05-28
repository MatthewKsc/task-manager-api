package com.matthewksc.taskmanager.controllers;

import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.entity.User;
import com.matthewksc.taskmanager.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/taskList/{byUserId}")
    public List<TaskList> getAllTaskListByUser(@PathVariable Long byUserId){
        return userService.getAllTaskListByUser(byUserId);
    }

    @GetMapping("/tasks/{userId}")
    public List<Task> getTaskByUser(@PathVariable Long userId){
        return userService.getAllTaskByUser(userId);
    }

    @PostMapping("/{userId}")
    public TaskList saveTaskList(@RequestBody TaskList taskList, @PathVariable Long userId){
        return userService.saveTaskList(taskList, userId);
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }
}
