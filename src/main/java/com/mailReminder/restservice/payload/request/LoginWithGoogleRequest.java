package com.mailReminder.restservice.payload.request;

import javax.validation.constraints.NotBlank;

public class LoginWithGoogleRequest {
    @NotBlank
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
