package ru.thevalidator.timeattackracing.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class EventRegistrationRequest {

    @NotNull(message = "Missing parameter 'user_id'")
    private UUID userId;

    @NotNull(message = "Missing parameter 'vehicle_id'")
    private Long vehicleId;

    @NotNull(message = "Missing parameter 'category_id'")
    private Integer categoryId;

    @NotNull(message = "Missing parameter 'racing_number'")
    private Integer racingNumber;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getRacingNumber() {
        return racingNumber;
    }

    public void setRacingNumber(Integer racingNumber) {
        this.racingNumber = racingNumber;
    }

}
