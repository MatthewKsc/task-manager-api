package com.matthewksc.taskmanager.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Test
    void getAllUsers() {
    }

    @Test
    void getAllTaskListByUser() {
    }

    @Test
    void getTaskByUser() {
    }

    @Test
    void saveTaskList() {
    }

    @Test
    void saveUser() {
    }
}