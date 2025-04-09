package ru.thevalidator.timeattackracing.dto;

import java.util.List;

public class GroupedCrewListDto {

    private List<EventCategoryCrewsDto> data;

    public GroupedCrewListDto() {
    }

    public GroupedCrewListDto(List<EventCategoryCrewsDto> data) {
        this.data = data;
    }

    public List<EventCategoryCrewsDto> getData() {
        return data;
    }

    public void setData(List<EventCategoryCrewsDto> data) {
        this.data = data;
    }

}
