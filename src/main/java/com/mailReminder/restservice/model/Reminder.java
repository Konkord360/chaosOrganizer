package com.mailReminder.restservice.model;

import java.time.LocalTime;

public class Reminder {
    private String date;
    private LocalTime reminderTime;
    private String contents;
    private Payment payment;


    public Reminder(final Payment payment){
        if(payment != null)
        this.payment = payment;
        this.date = payment.getDeadline();
        this.reminderTime = LocalTime.of(17, 30);
        this.contents = "Reminding you about: " + payment.getPaymentTitle() + "Which is due to" + payment.getDeadline();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
