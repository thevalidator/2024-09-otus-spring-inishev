package ru.thevalidator.timeattackracing.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;
import ru.thevalidator.timeattackracing.exception.ItemNotFoundException;
import ru.thevalidator.timeattackracing.repository.ClassificationCategoryRepository;
import ru.thevalidator.timeattackracing.service.ClassificationCategoryService;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ClassificationCategoryServiceImpl implements ClassificationCategoryService {

    private final ClassificationCategoryRepository classificationCategoryRepository;

    public ClassificationCategoryServiceImpl(ClassificationCategoryRepository classificationCategoryRepository) {
        this.classificationCategoryRepository = classificationCategoryRepository;
    }

    @Override
    public List<ClassificationCategoryEntity> getAllCategories() {
        return classificationCategoryRepository.findAll();
    }

    @Override
    public List<ClassificationCategoryEntity> getAllCategoriesAreIn(Set<Integer> categoryIds) {
        var sorting = Sort.by(Sort.Direction.ASC, "name");
        return classificationCategoryRepository.findByIdIn(categoryIds, sorting);
    }

    @Override
    public ClassificationCategoryEntity getById(Integer categoryId) {
        return classificationCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Category not found [id=%s].", categoryId)));
    }

}
