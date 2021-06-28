package com.mailReminder.restservice.model;


import org.springframework.data.annotation.Id;

import javax.swing.*;

public class Reminder {
    @Id
    private String id;
    private String ownerLogin;
    private String date;
    private String hour;
    private String contents;

    public Reminder(){
        this.ownerLogin = null;
        this.date = null;
        this.hour = null;
        this.contents = null;
    }

    private Reminder(String ownerLogin, String date, String hour, String contents){
        this.ownerLogin = ownerLogin;
        this.date = date;
        this.hour = hour;
        this.contents = contents;
    }

    public String getOwnerLogin(){
        return this.ownerLogin;
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

    @Override
    public boolean equals(Object reminder)
    {
        if(reminder instanceof Reminder)
        return this.ownerLogin.equals(((Reminder) reminder).getOwnerLogin()) &&
                this.date.equals(((Reminder) reminder).getDate()) &&
                this.hour.equals(((Reminder) reminder).getHour()) &&
                this.contents.equals(((Reminder) reminder).getContents());
        else
            return false;

    }
}
