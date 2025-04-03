package ru.thevalidator.timeattackracing.dto;

import java.util.List;

public class GroupedCrewLapsDto {

    private List<CrewLapsDto> data;

    public List<CrewLapsDto> getData() {
        return data;
    }

    public void setData(List<CrewLapsDto> data) {
        this.data = data;
    }

}
