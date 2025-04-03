package ru.thevalidator.timeattackracing.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.EventEntity;
import ru.thevalidator.timeattackracing.entity.SessionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    Optional<SessionEntity> findById(@NotNull Long id);

    List<SessionEntity> findAllByEvent(@NotNull EventEntity event);

    @EntityGraph(value = "sessions-types")
    List<SessionEntity> findAllByEventId(@NotNull Long eventId, Sort ordinalNumber);

}