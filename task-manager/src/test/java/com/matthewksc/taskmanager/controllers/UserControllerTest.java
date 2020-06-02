package com.matthewksc.taskmanager.controllers;

import com.matthewksc.taskmanager.dao.entity.Task;
import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.entity.User;
import com.matthewksc.taskmanager.services.UserService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class,
        //disable our WebSecurityConfig
        useDefaultFilters = false,
        //we Adding specific filter to be use
        includeFilters = {@ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = UserController.class
        )}) //this include spring security to our test's
//this disable spring security filter to test endpoints (disable JWT)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final String BASIC_URL= "/user";
    private static final String GET_USERS= BASIC_URL+"/all";
    private static final String GET_USER_TASK_LISTS= BASIC_URL+"/taskList/";
    private static final String GET_USER_TASKS= BASIC_URL+"/tasks/";
    private static final String POST_TASK_LIST = BASIC_URL + "/";
    private static final String POST_USER = BASIC_URL+ "/register";


    @Test
    void getAllUsers() throws Exception{
        given(userService.getAllUsers()).willReturn(Arrays.asList(
                new User(),
                new User()
        ));

        mockMvc.perform(get(GET_USERS))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getAllTaskListByUser() throws Exception{
        given(userService.getAllTaskListByUser(1L)).willReturn(Arrays.asList(
                new TaskList(),
                new TaskList()
        ));

        mockMvc.perform(get(GET_USER_TASK_LISTS+1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(userService, times(1)).getAllTaskListByUser(1L);
    }

    @Test
    void getTaskByUser() throws Exception{
        given(userService.getAllTaskByUser(1L)).willReturn(Arrays.asList(
                new Task(),
                new Task()
        ));

        mockMvc.perform(get(GET_USER_TASKS+1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(userService, times(1)).getAllTaskByUser(1L);
    }

    @Test
    void saveTaskList() throws Exception{
        mockMvc.perform(post(POST_TASK_LIST+1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveUser() throws Exception{
        mockMvc.perform(post(POST_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}