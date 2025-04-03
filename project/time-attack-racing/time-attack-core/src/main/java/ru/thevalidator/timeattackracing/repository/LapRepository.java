package ru.thevalidator.timeattackracing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.LapEntity;

import java.util.List;

@Repository
public interface LapRepository extends JpaRepository<LapEntity, Long> {

    List<LapEntity> findLapsBySessionId(Long sessionId);

    @Query(nativeQuery = true,
    value =
    "select l.id, l.session_id, l.racing_number, l.lap_time " +
            "from lap l " +
            "where lap_time = (" +
                "select MIN(lap_time) " +
                "from lap l2 " +
                "where l2.racing_number = l.racing_number and session_id = :sessionId" +
            ") " +
            "and session_id = :sessionId " +
            "and id = (select MIN(id) " +
                "from lap l3 " +
                "where l3.lap_time = l.lap_time and l3.racing_number = l.racing_number and session_id = :sessionId" +
            ")")
    List<LapEntity> findAcsSortedCrewBestLapsBySessionId(@Param("sessionId") Long sessionId);

    List<LapEntity> findLapsBySessionIdAndRacingNumber(Long sessionId, Integer raceNumber);

}