package ru.thevalidator.timeattackracing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.ScopeEntity;

@Repository
public interface ScopeRepository extends JpaRepository<ScopeEntity, Integer> {

}