package ru.thevalidator.timeattackracing.dto;

import jakarta.validation.constraints.NotEmpty;

public class SignInRequest {

    @NotEmpty(message = "Missing parameter 'email'")
    private String email;

    @NotEmpty(message = "Missing parameter 'password'")
    private String password;

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

}
