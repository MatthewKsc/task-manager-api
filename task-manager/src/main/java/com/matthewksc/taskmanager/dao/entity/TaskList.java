package com.matthewksc.taskmanager.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matthewksc.taskmanager.dao.entity.Task;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore //prevent of nested calls
    private User user;

    public TaskList() {
    }

    public TaskList(String categoryOfTask, User user) {
        this.categoryOfTask = categoryOfTask;
        this.user = user;
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

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
