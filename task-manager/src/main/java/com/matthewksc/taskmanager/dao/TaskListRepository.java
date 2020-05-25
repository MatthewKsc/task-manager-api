package com.matthewksc.taskmanager.dao;

import com.matthewksc.taskmanager.dao.entity.TaskList;
import com.matthewksc.taskmanager.dao.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListRepository extends CrudRepository<TaskList, Long> {
    List<TaskList> findAllByUser(User user);
}
