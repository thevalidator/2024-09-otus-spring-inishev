package ru.thevalidator.timeattackracing.converter;

import org.springframework.stereotype.Component;
import ru.thevalidator.timeattackracing.dto.ClassificationCategoryDto;
import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;

@Component
public class ClassificationCategoryConverter {

    public ClassificationCategoryDto toClassificationCategoryDto(ClassificationCategoryEntity category) {
        ClassificationCategoryDto dto = new ClassificationCategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setMaxPowerLimit(category.getMaxPower());
        dto.setWheelDriveType(category.getWheelDriveType());
        return dto;
    }

}
