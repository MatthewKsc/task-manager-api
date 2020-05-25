package com.matthewksc.taskmanager.controllers;

import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.entity.User;
import com.matthewksc.taskmanager.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

    @GetMapping("/{byUserId}/taskList")
    public List<TaskList> getAllTaskListByUser(@PathVariable Long byUserId){
        return userService.getAllTaskListByUser(byUserId);
    }

    @PostMapping("/{userId}")
    public TaskList saveTaskList(@RequestBody TaskList taskList, @PathVariable Long userId){
        return userService.saveTaskList(taskList, userId);
    }
}
