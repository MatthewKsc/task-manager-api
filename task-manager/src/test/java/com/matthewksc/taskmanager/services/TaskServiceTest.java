package com.matthewksc.taskmanager.services;

import com.matthewksc.taskmanager.dao.TaskListRepository;
import com.matthewksc.taskmanager.dao.TaskRepository;
import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskListRepository taskListRepository;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @Test
    void findById() {
        Task task = new Task();
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        Task result = taskService.findById(1L);
        Task fake = new Task();

        assertAll(
                ()-> assertEquals(task, result),
                ()-> assertNotEquals(fake, result),
                ()-> assertThrows(RuntimeException.class, ()-> taskService.findById(2L))
        );
    }

    @Test
    void findAll() {
        given(taskRepository.findAll()).willReturn(Arrays.asList(
                new Task(),
                new Task()
                ));

        List<Task> result = (List<Task>) taskService.findAll();
        List<Task> fake = new ArrayList<>();

        assertAll(
                ()-> verify(taskRepository, times(1)).findAll(),
                ()-> assertEquals(2, result.size()),
                ()-> assertEquals(taskRepository.findAll(), result),
                ()-> assertNotEquals(fake, result)
        );
    }

    @Test
    void deleteById() {
        taskService.deleteById(2L);

        verify(taskRepository, times(1)).deleteById(2L);
        verify(taskRepository, times(0)).deleteById(1L);
    }

    @Test
    void getTaskByTaskList() {
        TaskList taskList = new TaskList();
        taskList.setTasks(Arrays.asList(
                new Task(),
                new Task()
        ));
        given(taskListRepository.findById(1L)).willReturn(Optional.of(taskList));
        given(taskRepository.findAllByTaskList(taskList)).willReturn(taskList.getTasks());

        List<Task> result = taskService.getTaskByTaskList(1L);
        List<Task> fake = new ArrayList<>();

        assertAll(
                ()-> verify(taskRepository, times(1)).findAllByTaskList(taskList),
                ()-> verify(taskListRepository, times(1)).findById(1L),
                ()-> assertEquals(2, result.size()),
                ()-> assertEquals(taskList.getTasks(), result),
                ()-> assertNotEquals(fake, result),
                ()-> assertThrows(RuntimeException.class , ()-> taskService.getTaskByTaskList(2L)));
    }

    @Test
    void save() {
        TaskList taskList = new TaskList();
        Task task = new Task();
        given(taskListRepository.findById(1L)).willReturn(Optional.of(taskList));
        given(taskRepository.save(task)).willReturn(task);

        Task result = taskService.save(task, 1L);
        Task fake = new Task();

        assertAll(
                ()-> verify(taskListRepository, times(1)).findById(1L),
                ()-> verify(taskRepository, times(1)).save(task),
                ()-> assertEquals(task, result),
                ()-> assertNotEquals(fake, result),
                ()-> assertThrows(RuntimeException.class, ()-> taskService.save(task, 2L))
        );
    }
}