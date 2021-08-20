package com.mailReminder.restservice.model;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;


public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String login;
    private String mail;
    private List<Reminder> reminders;

    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private char[] password;
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
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
                "login='" + login + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + Arrays.toString(password) + '\'' +
                '}';
    }
}
