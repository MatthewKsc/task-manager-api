package com.matthewksc.taskmanager.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {

    @Test
    void getAllTasks() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void getTaskByTaskList() {
    }

    @Test
    void saveTask() {
    }

    @Test
    void deleteTaskById() {
    }
}