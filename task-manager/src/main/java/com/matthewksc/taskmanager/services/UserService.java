package com.matthewksc.taskmanager.services;

import com.matthewksc.taskmanager.dao.TaskListRepository;
import com.matthewksc.taskmanager.dao.TaskRepository;
import com.matthewksc.taskmanager.dao.UserRepository;
import com.matthewksc.taskmanager.dao.entity.Role;
import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private TaskListRepository taskListRepository;
    private TaskRepository taskRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TaskListRepository taskListRepository,
                       TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<TaskList> getAllTaskListByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Can't find user with id: "+userId));
        List<TaskList> taskLists = taskListRepository.findAllByUser(user);
        return taskLists;
    }

    public List<Task> getAllTaskByUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Can't find user with id: "+userId));
        List<Task> tasks = taskRepository.findAllByTaskList_User(user);
        return tasks;
    }

    public TaskList saveTaskList(TaskList taskList, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Can't find user with id: "+userId));
        taskList.setUser(user);
        taskListRepository.save(taskList);
        return taskList;
    }

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }
}
