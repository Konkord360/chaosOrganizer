package com.mailReminder.restservice.controller;

import com.mailReminder.restservice.model.User;
import com.mailReminder.restservice.repository.UserRepository;
import com.mailReminder.restservice.security.PasswordSecurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User newUser) {
        byte[] salt = PasswordSecurer.getSalt();
        byte[] hashedPassword = PasswordSecurer.hashPassword(Arrays.toString(newUser.getPassword()).toCharArray(), salt);

        if (hashedPassword == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");

        if (userRepository.findByLogin(newUser.getLogin()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"errorCode\":400,\"errorMessage\":\"User already exists\"}");

        System.out.println(Arrays.toString(hashedPassword));
        newUser.setPasswordFromBytes(hashedPassword);
        newUser.setSalt(salt);
        userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/register")
    public @ResponseBody
    Iterable<User> getAllUsers() {

        return userRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user){


        return  ResponseEntity.ok(user);
    }
}
