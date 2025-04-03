package ru.thevalidator.timeattackracing.dto;

public class LapRestDto {

    private Integer racingNumber;

    private String lapTime;

    public Integer getRacingNumber() {
        return racingNumber;
    }

    public void setRacingNumber(Integer racingNumber) {
        this.racingNumber = racingNumber;
    }

    public String getLapTime() {
        return lapTime;
    }

    public void setLapTime(String lapTime) {
        this.lapTime = lapTime;
    }

}
