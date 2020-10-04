package com.mailReminder.restservice.repository;

import com.mailReminder.restservice.model.Reminder;
import com.mailReminder.restservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReminderRepository extends CrudRepository<Reminder, Integer> {
    List<Reminder> findByUser(User user);
    Reminder findByReminderId(long id);
}