package com.mailReminder.restservice.repository;

import com.mailReminder.restservice.model.Reminder;
import com.mailReminder.restservice.model.User;
import org.springframework.data.repository.CrudRepository;

public interface ReminderRepository extends CrudRepository<Reminder, Integer> {
    User findByUser(User user);
    User findByReminderId(long id);
}