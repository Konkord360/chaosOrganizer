package com.mailReminder.restservice.repository;

import com.mailReminder.restservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByUsername(String Login);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}