package com.matthewksc.taskmanager.dao;

import com.matthewksc.taskmanager.dao.entity.TaskList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepository extends CrudRepository<TaskList, Long> {
}
