package com.mailReminder.restservice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import com.mailReminder.restservice.model.Reminder;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "user")
    private List<Reminder> reminders;

    private String login;
    private String mail;
    private char[] password;
    private byte[] salt;

    public User(String login, String mail, char[] password) {
        this.login = login;
        this.mail = mail;
        this.password = password;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public User() {
        this.id = 0L;
        this.login = "";
        this.mail = "";
        this.password = null;
        this.salt = null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setPasswordFromBytes(byte[] password){
        this.password = Base64.getEncoder().encodeToString(password).toCharArray();
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
