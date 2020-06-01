package com.matthewksc.taskmanager.services;

import com.matthewksc.taskmanager.dao.TaskListRepository;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskListServiceTest {

    @Mock
    TaskListRepository taskListRepository;

    @InjectMocks
    TaskListService taskListService;

    @Test
    void findAll() {
        given(taskListRepository.findAll()).willReturn(Arrays.asList(
                new TaskList(),
                new TaskList()
        ));

        List<TaskList> result = (List<TaskList>) taskListService.findAll();
        List<TaskList> fake = new ArrayList<>();

        assertAll(
                ()-> verify(taskListRepository, times(1)).findAll(),
                ()-> assertEquals(2, result.size()),
                ()-> assertEquals(taskListRepository.findAll(), result),
                ()-> assertNotEquals(fake, result)
        );
    }
    @Test
    void findById() {
        TaskList list1 = new TaskList();
        TaskList list2 = new TaskList();
        given(taskListRepository.findById(1L)).willReturn(java.util.Optional.of(list1));

        TaskList result = taskListService.findById(1L);

        assertAll(
                ()-> assertEquals(list1, result),
                ()-> assertNotEquals(list2, result),
                ()-> assertThrows(RuntimeException.class, ()-> taskListService.findById(2L))
        );
    }

    @Test
    void save() {
        TaskList list1 = new TaskList();
        TaskList list2 = new TaskList();
        given(taskListRepository.save(list1)).willReturn(list1);

        TaskList result = taskListService.save(list1);

        assertAll(
                ()-> assertEquals(list1, result),
                ()-> assertNotEquals(list2, result),
                ()-> verify(taskListRepository, times(1)).save(list1)
        );
    }

    @Test
    void deleteById() {
        taskListService.deleteById(1L);

        verify(taskListRepository, times(1)).deleteById(1L);
        verify(taskListRepository, times(0)).deleteById(2L);
    }
}