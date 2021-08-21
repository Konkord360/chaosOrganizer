package com.mailReminder.restservice.repository;

import com.mailReminder.restservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByLogin(String Login);
}