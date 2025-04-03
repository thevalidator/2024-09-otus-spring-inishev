package ru.thevalidator.timeattackracing.dto;

import jakarta.validation.constraints.NotNull;

public class CreateTrackRequest {

    @NotNull(message = "Parameter 'track_name' can't be blank")
    private String trackName;

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

}
