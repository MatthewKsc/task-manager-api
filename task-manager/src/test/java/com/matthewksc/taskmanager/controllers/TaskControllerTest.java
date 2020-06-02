package com.matthewksc.taskmanager.controllers;

import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskController.class,
        //disable our WebSecurityConfig
        useDefaultFilters = false,
        //we Adding specific filter to be use
        includeFilters = {@ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = TaskController.class
        )}) //this include spring security to our test's
//this disable spring security filter to test endpoints (disable JWT)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private static String BASIC_URL = "/task";
    private static String ALL_TASKS_URL = BASIC_URL+"/all";
    private static String GET_POST_DELETE_TASK = BASIC_URL+"/";
    private static String GET_TASKS_BY_TASK_LIST = BASIC_URL+"/byTaskList/";

    @Test
    void getAllTasks() throws Exception{
        given(taskService.findAll()).willReturn(Arrays.asList(
                new Task(),
                new Task()
        ));

        mockMvc.perform(get(ALL_TASKS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(taskService, times(1)).findAll();
    }

    @Test
    void getTaskById() throws Exception{
        Task task = new Task("test","title-date", new Date(), new TaskList());
        given(taskService.findById(1L)).willReturn(task);

        mockMvc.perform(get(GET_POST_DELETE_TASK+1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(task.getId())))
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));

        verify(taskService, times(1)).findById(1L);

    }

    @Test
    void getTaskByTaskList() throws Exception{
        given(taskService.getTaskByTaskList(1L)).willReturn(Arrays.asList(
                new Task(),
                new Task()
        ));

        mockMvc.perform(get(GET_TASKS_BY_TASK_LIST+1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(taskService, times(1)).getTaskByTaskList(1L);
    }

    @Test
    void saveTask() throws Exception{
        mockMvc.perform(post(GET_POST_DELETE_TASK+1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTaskById() throws Exception{
        mockMvc.perform(delete(GET_POST_DELETE_TASK+1))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteById(1L);
    }
}