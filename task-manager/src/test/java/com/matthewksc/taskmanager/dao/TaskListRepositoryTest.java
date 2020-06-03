package com.matthewksc.taskmanager.dao;

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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TaskListRepositoryTest {

    private TaskListRepository taskListRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskListRepositoryTest(TaskListRepository taskListRepository, UserRepository userRepository) {
        this.taskListRepository = taskListRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setData(){
        //bcs every test initData is invoked id is incremented*
        initData();
    }

    @AfterEach
    public void deleteData(){
        taskListRepository.deleteAll();
    }

    @Test
    void getTaskListById() {
        List<TaskList> list = (List<TaskList>) taskListRepository.findAll();
        TaskList taskList1 = taskListRepository.findById(list.get(0).getId()).get();
        TaskList taskList2 = taskListRepository.findById(list.get(1).getId()).get();

        assertAll(
                //task1 testing
                ()-> assertEquals(taskList1.getCategoryOfTask(), "test1"),
                ()-> assertEquals(taskList1.getId(), list.get(0).getId()),
                ()-> assertNotEquals(taskList1.getCategoryOfTask(), "test2"),
                ()-> assertNotEquals(taskList1.getCategoryOfTask(), "test3"),

                //task2 testing
                ()-> assertEquals(taskList2.getCategoryOfTask(), "test2"),
                ()-> assertEquals(taskList2.getId(), list.get(1).getId()),
                ()-> assertNotEquals(taskList2.getCategoryOfTask(), "test1"),
                ()-> assertNotEquals(taskList2.getCategoryOfTask(), "test3")
        );
    }

    @Test
    void getAllTaskLists() {
        List<TaskList> result = (List<TaskList>) taskListRepository.findAll();

        assertAll(
                ()-> assertEquals(3, result.size()),
                ()-> assertNotEquals(4, result.size()),
                ()-> assertNotEquals(2, result.size())
        );
    }

    @Test
    void deleteTaskById(){
        List<TaskList> taskLists = (List<TaskList>) taskListRepository.findAll();
        taskListRepository.deleteById(taskLists.get(0).getId());
        List<TaskList> result = (List<TaskList>) taskListRepository.findAll();

        assertAll(
                ()-> assertEquals(2, result.size()),
                ()-> assertNotEquals(3, result.size())
        );
    }

    @Test
    void saveTask(){
        TaskList newList = new TaskList();

        taskListRepository.save(newList);
        List<TaskList> result = (List<TaskList>) taskListRepository.findAll();

        assertAll(
                ()-> assertEquals(4, result.size()),
                ()-> assertNotEquals(3, result.size())
        );
    }

    @Test
    void findAllByUser() {
        User user = new User();
        TaskList taskList = new TaskList("testUser", user);
        taskListRepository.save(taskList);
        userRepository.save(user);

        List<TaskList> result = taskListRepository.findAllByUser(user);

        assertAll(
                ()-> assertEquals(1, result.size()),
                ()-> assertEquals("testUser", result.get(0).getCategoryOfTask()),
                ()-> assertNotEquals(3, result.size())
        );
    }

    private void initData() {
        TaskList list1 = new TaskList();
        TaskList list2 = new TaskList();
        TaskList list3 = new TaskList();
        AtomicInteger i = new AtomicInteger();
        List<TaskList> taskLists = Arrays.asList(list1,list2, list3);
        taskLists.forEach(e -> e.setCategoryOfTask("test"+i.incrementAndGet()));

        taskListRepository.saveAll(taskLists);
    }
}