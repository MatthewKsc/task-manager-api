package com.matthewksc.taskmanager.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskListController.class)
class TaskListControllerTest {

    @Test
    void getAllLists() {
    }

    @Test
    void getListById() {
    }

    @Test
    void saveTaskList() {
    }

    @Test
    void deleteListById() {
    }
}