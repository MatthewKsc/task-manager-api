package com.matthewksc.taskmanager.services;

import com.matthewksc.taskmanager.dao.TaskListRepository;
import com.matthewksc.taskmanager.dao.TaskRepository;
import com.matthewksc.taskmanager.dao.UserRepository;
import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    TaskListRepository taskListRepository;
    @Mock
    TaskRepository taskRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(
                new User(),
                new User()
        );
        given(userRepository.findAll()).willReturn(users);

        List<User> result = (List<User>) userService.getAllUsers();
        List<User> fake = new ArrayList<>();

        assertAll(
                ()-> verify(userRepository, times(1)).findAll(),
                ()-> assertEquals(users, result),
                ()-> assertEquals(2, result.size()),
                ()-> assertNotEquals(fake, result),
                ()-> assertNotEquals(1, result.size())
        );
    }

    @Test
    void saveUser() {
        User user = new User();
        given(userRepository.save(user)).willReturn(user);

        User result = userService.saveUser(user);
        User fake = new User();

        assertAll(
                ()-> verify(passwordEncoder, times(1)).encode(user.getPassword()),
                ()-> verify(userRepository, times(1)).save(user),
                ()-> assertEquals(user, result),
                ()-> assertNotEquals(fake, result)
        );
    }

    @Test
    void getAllTaskListByUser() {
        User user = new User();
        user.setTaskLists(Arrays.asList(
                new TaskList(),
                new TaskList()
        ));
        given(userRepository.findById(1L)).willReturn(java.util.Optional.of(user));
        given(taskListRepository.findAllByUser(user)).willReturn(user.getTaskLists());

        List<TaskList> result = userService.getAllTaskListByUser(1L);
        List<TaskList> fake = new ArrayList<>();

        assertAll(
                ()-> verify(userRepository, times(1)).findById(1L),
                ()-> verify(taskListRepository, times(1)).findAllByUser(user),
                ()-> assertEquals(user.getTaskLists(), result),
                ()-> assertEquals(2, result.size()),
                ()-> assertNotEquals(1, result.size()),
                ()-> assertNotEquals(fake, result),
                ()-> assertThrows(RuntimeException.class, ()-> userService.getAllTaskByUser(2L))
        );
    }

    @Test
    void getAllTaskByUser() {
        TaskList taskList = new TaskList();
        taskList.setTasks(Arrays.asList(
                new Task(),
                new Task()
        ));
        User user = new User();
        user.setTaskLists(Collections.singletonList(taskList));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(taskRepository.findAllByTaskList_User(user)).willReturn(taskList.getTasks());

        List<Task> result = userService.getAllTaskByUser(1L);
        List<Task> fake = new ArrayList<>();

        assertAll(
                ()-> verify(userRepository, times(1)).findById(1L),
                ()-> verify(taskRepository, times(1)).findAllByTaskList_User(user),
                ()-> assertEquals(taskList.getTasks(), result),
                ()-> assertEquals(2 ,result.size()),
                ()-> assertNotEquals(1, result.size()),
                ()-> assertNotEquals(fake, result),
                ()-> assertThrows(RuntimeException.class, ()-> userService.getAllTaskByUser(2L))
        );
    }

    @Test
    void saveTaskList() {
        User user = new User();
        TaskList taskList = new TaskList();
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(taskListRepository.save(taskList)).willReturn(taskList);

        TaskList result = userService.saveTaskList(taskList, 1L);
        TaskList fake = new TaskList();

        assertAll(
                ()-> verify(userRepository, times(1)).findById(1L),
                ()-> verify(taskListRepository, times(1)).save(taskList),
                ()-> assertEquals(taskList, result),
                ()-> assertNotEquals(fake, result),
                ()-> assertThrows(RuntimeException.class, ()-> userService.getAllTaskByUser(2L))
        );
    }
}