package com.mailReminder.restservice.model;

import java.math.BigDecimal;

public class Payment {
    private String ownerLogin;
    private String paymentTitle;
    private BigDecimal amountOfSinglePayment;
    private BigDecimal wholeAmount;
    private String deadline;
    private String receiverIBAN;
    private String receiverName;
    private String senderIBAN;
    private BigDecimal payedByNow;

    public Payment(String ownerLogin, String paymentTitle, BigDecimal amountOfSinglePayment, BigDecimal wholeAmount, String deadline, String receiverIBAN, String receiverName, String senderIBAN, BigDecimal payedByNow) {
        this.ownerLogin = ownerLogin;
        this.paymentTitle = paymentTitle;
        this.amountOfSinglePayment = amountOfSinglePayment;
        this.wholeAmount = wholeAmount;
        this.deadline = deadline;
        this.receiverIBAN = receiverIBAN;
        this.receiverName = receiverName;
        this.senderIBAN = senderIBAN;
        this.payedByNow = payedByNow;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public BigDecimal getAmountOfSinglePayment() {
        return amountOfSinglePayment;
    }

    public void setAmountOfSinglePayment(BigDecimal amountOfSinglePayment) {
        this.amountOfSinglePayment = amountOfSinglePayment;
    }

    public BigDecimal getWholeAmount() {
        return wholeAmount;
    }

    public void setWholeAmount(BigDecimal wholeAmount) {
        this.wholeAmount = wholeAmount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    public void setReceiverIBAN(String receiverIBAN) {
        this.receiverIBAN = receiverIBAN;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderIBAN() {
        return senderIBAN;
    }

    public void setSenderIBAN(String senderIBAN) {
        this.senderIBAN = senderIBAN;
    }

    public BigDecimal getPayedByNow() {
        return payedByNow;
    }

    public void setPayedByNow(BigDecimal payedByNow) {
        this.payedByNow = payedByNow;
    }
}
