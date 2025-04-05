package ru.thevalidator.timeattackracing.dto;

import java.util.List;

public class SessionResultByCategoryDto {

    private ClassificationCategoryDto classificationCategory;

    private List<CrewLapsDto> data;

    public ClassificationCategoryDto getClassificationCategory() {
        return classificationCategory;
    }

    public void setClassificationCategory(ClassificationCategoryDto classificationCategory) {
        this.classificationCategory = classificationCategory;
    }

    public List<CrewLapsDto> getData() {
        return data;
    }

    public void setData(List<CrewLapsDto> data) {
        this.data = data;
    }

}
