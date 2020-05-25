package com.matthewksc.taskmanager;

import com.matthewksc.taskmanager.dao.UserRepository;
import com.matthewksc.taskmanager.dao.entity.Role;
import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.TaskListRepository;
import com.matthewksc.taskmanager.dao.TaskRepository;
import com.matthewksc.taskmanager.dao.entity.User;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class init {

    private TaskListRepository taskListRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public init(TaskListRepository taskListRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        User user = new User("user", "user123", "user@wp.pl", Role.ROLE_USER);
        userRepository.save(user);

        TaskList taskList = new TaskList("My Tasks", user);
        taskListRepository.save(taskList);

        Task task = new Task("comapny","need to do raport", new Date(), taskList);
        taskRepository.save(task);
        Task task2 = new Task("home","wash dishes", new Date(), taskList);
        taskRepository.save(task2);
    }
}
