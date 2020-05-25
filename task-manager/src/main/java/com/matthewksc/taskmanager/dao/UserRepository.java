package com.matthewksc.taskmanager.dao;

import com.matthewksc.taskmanager.dao.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
