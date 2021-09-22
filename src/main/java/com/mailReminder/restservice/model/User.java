package com.mailReminder.restservice.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private List<Reminder> reminders;
    private List<Payment> payments;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Size(max = 120)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] salt;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String id, String username, String email, List<Reminder> reminders, List<Payment> payments, String password, byte[] salt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.reminders = reminders;
        this.payments = payments;
        this.password = password;
        this.salt = salt;
    }

    public User(String username, String email, List<Reminder> reminders, List<Payment> payments, String password, byte[] salt) {
        this.username = username;
        this.email = email;
        this.reminders = reminders;
        this.payments = payments;
        this.password = password;
        this.salt = salt;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public User() {
        this.username = "";
        this.email = "";
        this.password = null;
        this.salt = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
