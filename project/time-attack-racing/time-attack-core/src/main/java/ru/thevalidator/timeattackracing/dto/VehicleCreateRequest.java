package ru.thevalidator.timeattackracing.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class VehicleCreateRequest {

    @NotNull
    private UUID userId;

    @Size(max = 255)
    @NotNull
    private String make;

    @Size(max = 255)
    @NotNull
    private String model;

    @NotNull
    @Min(1900)
    private Integer year;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
