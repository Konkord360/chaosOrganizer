package com.mailReminder.restservice.controller;

import com.mailReminder.restservice.model.User;
import com.mailReminder.restservice.repository.UserRepository;
import com.mailReminder.restservice.security.PasswordSecurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

    private static final String template = "Hello, %s!, %s, %s";
    private final AtomicLong counter = new AtomicLong();

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestParam(value = "login", defaultValue = "Goreng") String login,
                                   @RequestParam(value = "mail", defaultValue = "") String mail,
                                   @RequestParam(value = "password", defaultValue = "") char[] password) {

        byte[] salt = PasswordSecurer.getSalt();
        byte[] hashedPassword = PasswordSecurer.hashPassword(password, salt);
        password = new char[password.length];

        User testUser = userRepository.findByLogin(login);
        if (testUser != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"errorCode\":400,\"errorMessage\":\"User already exists\"}");

        User user = new User(login, mail, hashedPassword, salt);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/register")
    public @ResponseBody
    Iterable<User> getAllUsers() {

        return userRepository.findAll();
    }
}
