package com.matthewksc.taskmanager.controllers;

import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.services.TaskListService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskListController.class,
        //disable our WebSecurityConfig
        useDefaultFilters = false,
        //we Adding specific filter to be use
        includeFilters = {@ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = TaskListController.class
        )}) //this include spring security to our test's
//this disable spring security filter to test endpoints (disable JWT)
@AutoConfigureMockMvc(addFilters = false)
class TaskListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskListService taskListService;

    private static final String BASIC_URL = "/tasks/lists";
    private static final String GET_DELETE_TASK_LIST = "/tasks/lists/";

    @Test
    void getAllLists() throws Exception {
        given(taskListService.findAll()).willReturn(Arrays.asList(
                new TaskList(),
                new TaskList()
        ));
        mockMvc.perform(get(BASIC_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(taskListService, times(1)).findAll();
    }

    @Test
    void getListById() throws Exception {
        TaskList taskList = new TaskList();
        taskList.setCategoryOfTask("test");
        given(taskListService.findById(1L)).willReturn(taskList);

        mockMvc.perform(get(GET_DELETE_TASK_LIST+1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(taskList.getId())))
                .andExpect(jsonPath("$.tasks", is(taskList.getTasks())))
                .andExpect(jsonPath("$.categoryOfTask", is(taskList.getCategoryOfTask())));
        verify(taskListService, times(1)).findById(1L);
    }

    @Test
    void saveTaskList() throws Exception{
        mockMvc.perform(post(BASIC_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteListById() throws Exception{
        mockMvc.perform(delete(GET_DELETE_TASK_LIST+1))
                .andExpect(status().isOk());

        verify(taskListService, times(1)).deleteById(1L);
    }
}