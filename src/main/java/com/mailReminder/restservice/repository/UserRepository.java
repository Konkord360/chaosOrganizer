package com.mailReminder.restservice.repository;

import com.mailReminder.restservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByLogin(String Login);
}