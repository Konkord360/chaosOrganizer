package com.mailReminder.restservice.model;

import java.math.BigDecimal;

public class Payment {
    private String ownerLogin;
    private BigDecimal amountOfOnePayment;
    private BigDecimal finalAmount;
    private String payDeadline;

    public Payment(String ownerLogin, BigDecimal amountOfOnePayment, BigDecimal finalAmount, String payDeadline) {
        this.ownerLogin = ownerLogin;
        this.amountOfOnePayment = amountOfOnePayment;
        this.finalAmount = finalAmount;
        this.payDeadline = payDeadline;
    }

    public String getOwnerLogin() {
        return this.ownerLogin;
    }

    public BigDecimal getAmountOfOnePayment() {
        return this.amountOfOnePayment;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public void setAmountOfOnePayment(BigDecimal amountOfOnePayment) {
        this.amountOfOnePayment = amountOfOnePayment;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getPayDeadline() {
        return payDeadline;
    }

    public void setPayDeadline(String payDeadline) {
        this.payDeadline = payDeadline;
    }
}
