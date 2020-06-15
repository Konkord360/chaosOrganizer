package com.mailReminder.restservice.repository;

import com.mailReminder.restservice.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByLogin(String Login);
    User findById(long id);
}