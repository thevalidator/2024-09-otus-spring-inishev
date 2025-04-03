package ru.thevalidator.timeattackracing.service;

import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;

import java.util.List;
import java.util.Set;

public interface ClassificationCategoryService {

    List<ClassificationCategoryEntity> getAllCategories();

    List<ClassificationCategoryEntity> getAllCategoriesAreIn(Set<Integer> categoryIds);

    ClassificationCategoryEntity getById(Integer categoryId);

}
