package com.mailReminder.restservice.controller;

import com.mailReminder.restservice.model.Reminder;
import com.mailReminder.restservice.model.User;
import com.mailReminder.restservice.repository.ReminderRepository;
import com.mailReminder.restservice.repository.UserRepository;
import com.mailReminder.restservice.security.PasswordSecurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final ReminderRepository reminderRepository;

    @Autowired
    public UserController(UserRepository userRepository, ReminderRepository reminderRepository) {
        this.userRepository = userRepository;
        this.reminderRepository = reminderRepository;
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

    @GetMapping("/getUsers")
    public @ResponseBody
    Iterable<User> getAllUsers() {

        return userRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user){
        User userFromDatabase = userRepository.findByLogin(user.getLogin());
        if (userFromDatabase == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"errorCode\":400,\"errorMessage\":\"User does not exists\"}");

        byte[] saltFromDatabase = userFromDatabase.getSalt();
        user.setPasswordFromBytes(PasswordSecurer.hashPassword(Arrays.toString(user.getPassword()).toCharArray(), saltFromDatabase));

        if(Arrays.equals(userFromDatabase.getPassword(), user.getPassword())) {
            user.setPassword(null);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"errorCode\":400,\"errorMessage\":\"Password not valid\"}");
    }

    @PostMapping("/addReminder")
    public ResponseEntity<Object> addReminder(@RequestBody Reminder reminder){
        User databaseUser = userRepository.findByLogin(reminder.getOwnerLogin());

        reminder.setUser(databaseUser);
        reminderRepository.save(reminder);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping("/getReminders")
    public ResponseEntity<Object> getReminder(@RequestParam String userLogin){
        User user = userRepository.findByLogin(userLogin);
        StringBuilder remindersJson = new StringBuilder("{\"reminders\":[");
        List<Reminder> userReminders = user.getReminders();

        for (int i = 0; i < userReminders.size(); i++) {
            remindersJson.append("{\"contents\":\"");
            remindersJson.append(userReminders.get(i).getContents().concat("\","));
            remindersJson.append("\"date\":\"");
            remindersJson.append(userReminders.get(i).getDate().concat("\","));
            remindersJson.append("\"hour\":\"");
            remindersJson.append(userReminders.get(i).getHour().concat("\"}"));
            if(i < userReminders.size() - 1)
                remindersJson.append(",");
        }
        remindersJson.append("]}");
        return ResponseEntity.status(HttpStatus.OK).body(remindersJson.toString());
    }
}
