package com.matthewksc.taskmanager.controllers;

import com.matthewksc.taskmanager.dao.entity.MyUserDetails;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.entity.User;
import com.matthewksc.taskmanager.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/{byUserId}/taskList")
    public List<TaskList> getAllTaskListByUser(@PathVariable Long byUserId){
        return userService.getAllTaskListByUser(byUserId);
    }

    @PostMapping("/{userId}")
    public TaskList saveTaskList(@RequestBody TaskList taskList, @PathVariable Long userId){
        return userService.saveTaskList(taskList, userId);
    }
}
