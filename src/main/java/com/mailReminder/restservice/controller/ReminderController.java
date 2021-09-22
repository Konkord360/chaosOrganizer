package com.mailReminder.restservice.controller;

import com.mailReminder.restservice.model.Reminder;
import com.mailReminder.restservice.model.User;
import com.mailReminder.restservice.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
public class ReminderController {
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(ReminderController.class);

    @Autowired
    public ReminderController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PatchMapping("/addReminder")
    public ResponseEntity<Object> addReminder(@RequestBody Reminder reminder) {
        if(logger.isDebugEnabled()) {
            logger.debug(reminder.toString());
        }
        User databaseUser = userRepository.findByUsername(reminder.getOwnerLogin());

        Optional<List<Reminder>> userReminders = Optional.ofNullable(databaseUser.getReminders());
        if (userReminders.isPresent()) {
            userReminders.get().add(reminder);
            databaseUser.setReminders(userReminders.get());
        } else
            databaseUser.setReminders(List.of(reminder));

        userRepository.save(databaseUser);
        return ResponseEntity.status(HttpStatus.OK).body("{\"errorCode\":0,\"errorMessage\":\"OK\"}");
    }

    @GetMapping("/getReminders")
    public ResponseEntity<Object> getReminder(@RequestParam String userLogin) {
        User user = userRepository.findByUsername(userLogin);
        List<Reminder> userReminders = user.getReminders();

        return ResponseEntity.status(HttpStatus.OK).body(userReminders);
    }

    @DeleteMapping("/deleteReminder")
    public ResponseEntity<Object> deleteReminder(@RequestBody Reminder reminder) {
        User user = userRepository.findByUsername(reminder.getOwnerLogin());
        Optional<Reminder> reminderToBeDeleted = user.getReminders().stream().filter(userReminder -> userReminder.equals(reminder)).findFirst();
        if (reminderToBeDeleted.isPresent()) {
            user.getReminders().remove(reminderToBeDeleted.get());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("{\"Message\":\"Successfully deleted\"}");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"errorCode\":404,\"errorMessage\":\"Reminder not found\"}");
    }
}
