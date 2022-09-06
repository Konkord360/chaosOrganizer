package com.mailReminder.restservice.repository;

import com.mailReminder.restservice.model.ERole;
import com.mailReminder.restservice.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
    Role insert(Role role);
}
