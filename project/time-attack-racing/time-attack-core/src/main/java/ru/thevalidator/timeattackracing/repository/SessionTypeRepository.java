package ru.thevalidator.timeattackracing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.SessionTypeEntity;

@Repository
public interface SessionTypeRepository extends JpaRepository<SessionTypeEntity, Integer> {

}