package ru.thevalidator.timeattackracing.dto;

import java.util.UUID;

public class CrewDto {

    private UUID id;

    private String pilotName;

    private String vehicleName;

    private Integer racingNumber;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Integer getRacingNumber() {
        return racingNumber;
    }

    public void setRacingNumber(Integer racingNumber) {
        this.racingNumber = racingNumber;
    }

}
