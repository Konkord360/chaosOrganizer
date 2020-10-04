package com.mailReminder.restservice.model;

import javax.persistence.*;

@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reminderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @Transient
    private String ownerLogin;
    private String date;
    private String hour;
    private String contents;

    public Reminder(){
        this.ownerLogin = null;
        this.user = null;
        this.date = null;
        this.hour = null;
        this.contents = null;
    }

    private Reminder(String ownerLogin, String date, String hour, String contents){
        this.ownerLogin = ownerLogin;
        this.date = date;
        this.hour = hour;
        this.contents = contents;
        this.user = null;
    }

    public String getOwnerLogin(){
        return this.ownerLogin;
    }
    public long getReminderId(){
        return reminderId;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
