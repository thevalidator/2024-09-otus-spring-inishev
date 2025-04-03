package ru.thevalidator.timeattackracing.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface ClassificationCategoryRepository extends JpaRepository<ClassificationCategoryEntity, Integer> {

    @EntityGraph(value = "events-tracks-categories")
    List<ClassificationCategoryEntity> findByIdIn(Set<Integer> categoryIds, Sort ordinalNumber);

}