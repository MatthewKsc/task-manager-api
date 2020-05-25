package com.matthewksc.taskmanager;

import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.TaskListRepository;
import com.matthewksc.taskmanager.dao.TaskRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class init {

    private TaskListRepository taskListRepository;
    private TaskRepository taskRepository;

    public init(TaskListRepository taskListRepository, TaskRepository taskRepository) {
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void init(){
//        TaskList taskList = new TaskList("My Tasks");
//        taskListRepository.save(taskList);
//
//        Task task = new Task("comapny","need to do raport", new Date(), taskList);
//        taskRepository.save(task);
//        Task task2 = new Task("home","wash dishes", new Date(), taskList);
//        taskRepository.save(task2);
//    }
}
