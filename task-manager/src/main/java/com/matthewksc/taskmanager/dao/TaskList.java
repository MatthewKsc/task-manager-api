package com.matthewksc.taskmanager.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryOfTask;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "taskList")
    private List<Task> tasks = new ArrayList<>();

    public TaskList() {
    }

    public TaskList(String categoryOfTask) {
        this.tasks = tasks;
        this.categoryOfTask = categoryOfTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryOfTask() {
        return categoryOfTask;
    }

    public void setCategoryOfTask(String categoryOfTask) {
        this.categoryOfTask = categoryOfTask;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
