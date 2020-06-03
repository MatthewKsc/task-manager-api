package com.matthewksc.taskmanager.dao;

import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
@DataJpaTest
class TaskRepositoryTest {

    private TaskListRepository taskListRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskRepositoryTest(TaskListRepository taskListRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setData(){
        //bcs every test initData is invoked id is incremented*
        initData();
    }

    @AfterEach
    public void deleteData(){
        taskRepository.deleteAll();
    }

    @Test
    void getTaskById(){
        List<Task> tasks = (List<Task>) taskRepository.findAll();

        //bcs of setData we need to get id from list can't be hardcoded like 1L
        Task task1 = taskRepository.findById(tasks.get(0).getId()).get();
        Task task3 = taskRepository.findById(tasks.get(2).getId()).get();

        assertAll(
                //task1 testing
                ()-> assertEquals(task1.getTitle(), "test1"),
                ()-> assertEquals(task1.getId(), tasks.get(0).getId()),
                ()-> assertEquals(task1.getDescription(), "test1Desc"),
                ()-> assertNotEquals(task1.getTitle(), "test2"),
                ()-> assertNotEquals(task1.getTitle(), "test3"),

                //task2 testing
                ()-> assertEquals(task3.getTitle(), "test3"),
                ()-> assertEquals(task3.getDescription(), "test3Desc"),
                ()-> assertNotEquals(task3.getTitle(), "test1"),
                ()-> assertNotEquals(task3.getTitle(), "test2")
        );
    }

    @Test
    void getAllTasks(){
        List<Task> result = (List<Task>) taskRepository.findAll();

        //bcs of setData we need to get id from list can't be hardcoded like 1L
        assertAll(
                ()-> assertEquals(3, result.size()),
                ()-> assertEquals("test1", result.get(0).getTitle()),
                ()-> assertEquals("test2", result.get(1).getTitle()),
                ()-> assertNotEquals("test3", result.get(1).getTitle()),
                ()-> assertNotEquals(2, result.size())
        );
    }

    @Test
    void deleteTaskById(){
        List<Task> tasks= (List<Task>) taskRepository.findAll();
        taskRepository.deleteById(tasks.get(0).getId());
        List<Task> result = (List<Task>) taskRepository.findAll();

        assertAll(
                ()-> assertEquals(2, result.size()),
                ()-> assertNotEquals(3, result.size())
        );
    }

    @Test
    void saveTask(){
        Task task = new Task();

        taskRepository.save(task);
        List<Task> result = (List<Task>) taskRepository.findAll();

        assertAll(
                ()-> assertEquals(4, result.size()),
                ()-> assertNotEquals(3, result.size())
        );
    }

    @Test
    void findAllByTaskList() {
        //we need to save taskList bcs of exception from unsaved entity
        TaskList taskList = new TaskList();
        Task task1 = new Task("test1", "test1Desc", new Date(), taskList);
        taskRepository.save(task1);
        taskListRepository.save(taskList);

        List<Task> result = taskRepository.findAllByTaskList(taskList);

        assertAll(
                ()-> assertEquals(1, result.size()),
                ()-> assertNotEquals(3, result.size())
        );
    }

    @Test
    void findAllByTaskList_User() {
        //we need to save user and taskList bcs of exception from unsaved entity
        User user = new User();
        TaskList taskList = new TaskList("testList", user);
        Task task1 = new Task("test1", "test1Desc", new Date(), taskList);
        taskRepository.save(task1);
        taskListRepository.save(taskList);
        userRepository.save(user);

        List<Task> result = taskRepository.findAllByTaskList_User(user);

        assertAll(
                ()-> assertEquals(1, result.size()),
                ()-> assertNotEquals(3, result.size())
        );
    }

    private void initData() {
        Task task1 = new Task("test1", "test1Desc", new Date());
        Task task2 = new Task("test2", "test2Desc", new Date());
        Task task3 = new Task("test3", "test3Desc", new Date());
        taskRepository.saveAll(Arrays.asList(task1,task2,task3));
    }
}