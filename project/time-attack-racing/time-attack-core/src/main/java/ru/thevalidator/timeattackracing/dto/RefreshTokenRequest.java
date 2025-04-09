package ru.thevalidator.timeattackracing.dto;

import jakarta.validation.constraints.NotEmpty;

public class RefreshTokenRequest {

    @NotEmpty(message = "Missing parameter 'refresh_token'")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
