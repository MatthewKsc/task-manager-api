package com.matthewksc.taskmanager.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepository extends CrudRepository<TaskList, Long> {
}
