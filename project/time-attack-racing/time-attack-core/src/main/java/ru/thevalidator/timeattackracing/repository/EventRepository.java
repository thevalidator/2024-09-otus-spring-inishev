package ru.thevalidator.timeattackracing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.EventEntity;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @EntityGraph(value = "events-tracks-categories")
    Optional<EventEntity> findById(Long eventId);

    @EntityGraph(value = "events-tracks-categories")
    Page<EventEntity> findAll(Pageable pageable);

}