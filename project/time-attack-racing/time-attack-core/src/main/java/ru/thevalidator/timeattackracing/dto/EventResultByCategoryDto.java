package ru.thevalidator.timeattackracing.dto;

import java.util.List;

public class EventResultByCategoryDto implements Comparable<EventResultByCategoryDto> {

    private ClassificationCategoryDto classificationCategory;

    private List<CrewResultDto> data;

    public EventResultByCategoryDto() {
    }

    public EventResultByCategoryDto(List<CrewResultDto> data) {
        this.data = data;
    }

    public ClassificationCategoryDto getClassificationCategory() {
        return classificationCategory;
    }

    public void setClassificationCategory(ClassificationCategoryDto classificationCategory) {
        this.classificationCategory = classificationCategory;
    }

    public List<CrewResultDto> getData() {
        return data;
    }

    public void setData(List<CrewResultDto> data) {
        this.data = data;
    }

    @Override
    public int compareTo(EventResultByCategoryDto o) {
        int result;
        var category = this.getClassificationCategory();
        var anotherCategory = o.getClassificationCategory();

        result = category.getName().compareTo(anotherCategory.getName());

        if (result == 0) {
            result = category.getMaxPowerLimit().compareTo(anotherCategory.getMaxPowerLimit());
        }

        if (result == 0) {
            result = category.getWheelDriveType().compareTo(anotherCategory.getWheelDriveType());
        }

        return result;
    }

}
