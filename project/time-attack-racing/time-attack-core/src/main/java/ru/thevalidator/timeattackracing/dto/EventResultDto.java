package ru.thevalidator.timeattackracing.dto;

import java.util.List;

public class EventResultDto {

    private List<CrewResultDto> data;

    public EventResultDto() {
    }

    public EventResultDto(List<CrewResultDto> data) {
        this.data = data;
    }

    public List<CrewResultDto> getData() {
        return data;
    }

    public void setData(List<CrewResultDto> data) {
        this.data = data;
    }

}
