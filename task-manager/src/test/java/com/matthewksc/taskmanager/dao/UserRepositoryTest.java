package com.matthewksc.taskmanager.dao;

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
class UserRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setData(){
        //bcs every test initData is invoked id is incremented*
        initData();
    }

    @AfterEach
    public void deleteData(){
        userRepository.deleteAll();
    }

    @Test
    void getTaskListById() {
        List<User> list = (List<User>) userRepository.findAll();
        User user1 = userRepository.findById(list.get(0).getId()).get();
        User user2 = userRepository.findById(list.get(1).getId()).get();

        assertAll(
                //user1 testing
                ()-> assertEquals(user1.getUsername(), "test1"),
                ()-> assertEquals(user1.getId(), list.get(0).getId()),
                ()-> assertNotEquals(user1.getUsername(), "test2"),
                ()-> assertNotEquals(user1.getUsername(), "test3"),

                //user2 testing
                ()-> assertEquals(user2.getUsername(), "test2"),
                ()-> assertEquals(user2.getId(), list.get(1).getId()),
                ()-> assertNotEquals(user2.getUsername(), "test1"),
                ()-> assertNotEquals(user2.getUsername(), "test3")
        );
    }

    @Test
    void getAllTaskLists() {
        List<User> result = (List<User>) userRepository.findAll();

        assertAll(
                ()-> assertEquals(3, result.size()),
                ()-> assertNotEquals(4, result.size()),
                ()-> assertNotEquals(2, result.size())
        );
    }

    @Test
    void deleteTaskById(){
        List<User> taskLists = (List<User>) userRepository.findAll();
        userRepository.deleteById(taskLists.get(0).getId());
        List<User> result = (List<User>) userRepository.findAll();

        assertAll(
                ()-> assertEquals(2, result.size()),
                ()-> assertNotEquals(3, result.size())
        );
    }

    @Test
    void saveTask(){
        User newUser = new User();

        userRepository.save(newUser);
        List<User> result = (List<User>) userRepository.findAll();

        assertAll(
                ()-> assertEquals(4, result.size()),
                ()-> assertNotEquals(3, result.size())
        );
    }

    @Test
    void findByUsername() {
        User result = userRepository.findByUsername("test1").get();

        assertAll(
                ()-> assertNotNull(result),
                ()-> assertEquals(result.getUsername(), "test1"),
                ()-> assertNotEquals(result.getUsername(), "test2")
        );
    }

    private void initData() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        AtomicInteger i = new AtomicInteger();
        List<User> users = Arrays.asList(user1,user2, user3);
        users.forEach(e -> e.setUsername(("test"+i.incrementAndGet())));

        userRepository.saveAll(users);
    }
}