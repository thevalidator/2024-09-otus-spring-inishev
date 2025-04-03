package ru.thevalidator.timeattackracing.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.thevalidator.timeattackracing.converter.ClassificationCategoryConverter;
import ru.thevalidator.timeattackracing.dto.ClassificationCategoryDto;
import ru.thevalidator.timeattackracing.service.ClassificationCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final ClassificationCategoryService classificationCategoryService;

    private final ClassificationCategoryConverter classificationCategoryConverter;

    public CategoryController(ClassificationCategoryService classificationCategoryService,
                              ClassificationCategoryConverter classificationCategoryConverter) {
        this.classificationCategoryService = classificationCategoryService;
        this.classificationCategoryConverter = classificationCategoryConverter;
    }

    @GetMapping("/categories")
    public List<ClassificationCategoryDto> getAllCategories() {
        var categories = classificationCategoryService.getAllCategories();
        return categories.stream().map(classificationCategoryConverter::toClassificationCategoryDto).toList();
    }


}
