package ru.thevalidator.timeattackracing.dto;

import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;

import java.util.List;

public class EventCategoryCrewsDto {

    private ClassificationCategoryEntity category;

    private List<CrewDto> crews;

    public ClassificationCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(ClassificationCategoryEntity category) {
        this.category = category;
    }

    public List<CrewDto> getCrews() {
        return crews;
    }

    public void setCrews(List<CrewDto> crews) {
        this.crews = crews;
    }

}
