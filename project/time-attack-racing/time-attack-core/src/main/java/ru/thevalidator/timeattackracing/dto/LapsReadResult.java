package ru.thevalidator.timeattackracing.dto;

import ru.thevalidator.timeattackracing.entity.LapEntity;

import java.util.List;

public class LapsReadResult {

    private List<LapEntity> laps;

    private int totalRecords;

    private int badRecords;

    public List<LapEntity> getLaps() {
        return laps;
    }

    public void setLaps(List<LapEntity> laps) {
        this.laps = laps;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getBadRecords() {
        return badRecords;
    }

    public void setBadRecords(int badRecords) {
        this.badRecords = badRecords;
    }

}
