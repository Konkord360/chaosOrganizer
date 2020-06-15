package com.mailReminder.restservice.model;

import javax.persistence.*;
import java.util.ServiceConfigurationError;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private final String login;
    private final String mail;
    private final byte[] password;
    private final byte[] salt;

    public User(String login, String mail, byte[] password, byte[] salt) {
        this.login = login;
        this.mail = mail;
        this.password = password;
        this.salt = salt;
    }

    public User(){
        this.id = 0L;
        this.login = "";
        this.mail = "";
        this.password = null;
        this.salt = null;
    }

    public String getLogin() {
        return login;
    }

    public String getMail() {
        return mail;
    }

    public byte[] getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public byte[] getSalt() {
        return salt;
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
