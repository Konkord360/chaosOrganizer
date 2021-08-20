package com.mailReminder.restservice.controller;

import com.mailReminder.restservice.model.Reminder;
import com.mailReminder.restservice.model.User;
import com.mailReminder.restservice.repository.UserRepository;
import com.mailReminder.restservice.security.PasswordSecurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        hashedPassword = null;

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

    @PatchMapping("/addReminder")
    public ResponseEntity<Object> addReminder(@RequestBody Reminder reminder){

        System.out.println(reminder.toString());
        User databaseUser = userRepository.findByLogin(reminder.getOwnerLogin());

        Optional<List<Reminder>> userReminders = Optional.ofNullable(databaseUser.getReminders());
        if(userReminders.isPresent()){
            userReminders.get().add(reminder);
            databaseUser.setReminders(userReminders.get());
        }else
            databaseUser.setReminders(List.of(reminder));

        userRepository.save(databaseUser);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping("/getReminders")
    public ResponseEntity<Object> getReminder(@RequestParam String userLogin){
        User user = userRepository.findByLogin(userLogin);
        List<Reminder> userReminders = user.getReminders();

        return ResponseEntity.status(HttpStatus.OK).body(userReminders);
    }

    @DeleteMapping("/deleteReminder")
    public ResponseEntity<Object> deleteReminder(@RequestBody Reminder reminder){
        User user = userRepository.findByLogin(reminder.getOwnerLogin());
        Optional<Reminder> reminderToBeDeleted = user.getReminders().stream().filter((userReminder) -> userReminder.equals(reminder)).findFirst();
        if(reminderToBeDeleted.isPresent()){
            user.getReminders().remove(reminderToBeDeleted.get());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("{\"Message\":\"Successfully deleted\"}");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"errorCode\":404,\"errorMessage\":\"Reminder not found\"}");
    }
}
