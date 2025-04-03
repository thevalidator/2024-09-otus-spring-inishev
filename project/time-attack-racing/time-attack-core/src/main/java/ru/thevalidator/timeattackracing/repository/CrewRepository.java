package ru.thevalidator.timeattackracing.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.CrewEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CrewRepository extends JpaRepository<CrewEntity, UUID> {

    @EntityGraph(value = "regs-categories-vehicles")
    List<CrewEntity> findAllByEventId(Long eventId);

    Optional<CrewEntity> findByRacingNumberAndEventId(Integer racingNumber, Long id);

}