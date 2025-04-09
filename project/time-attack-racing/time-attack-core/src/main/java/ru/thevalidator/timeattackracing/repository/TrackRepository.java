package ru.thevalidator.timeattackracing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.TrackEntity;

@Repository
public interface TrackRepository extends JpaRepository<TrackEntity, Long> {

}