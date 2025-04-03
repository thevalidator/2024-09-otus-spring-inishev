package ru.thevalidator.timeattackracing.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thevalidator.timeattackracing.converter.CrewConverter;
import ru.thevalidator.timeattackracing.dto.CrewLapsDto;
import ru.thevalidator.timeattackracing.dto.CrewResultDto;
import ru.thevalidator.timeattackracing.dto.EventResultDto;
import ru.thevalidator.timeattackracing.entity.CrewEntity;
import ru.thevalidator.timeattackracing.entity.SessionEntity;
import ru.thevalidator.timeattackracing.entity.SessionType;
import ru.thevalidator.timeattackracing.service.EventResultService;
import ru.thevalidator.timeattackracing.service.EventService;
import ru.thevalidator.timeattackracing.service.LapService;
import ru.thevalidator.timeattackracing.service.SessionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service
@Transactional(readOnly = true)
public class EventResultServiceImpl implements EventResultService {

    private static final long penalty = 10_000;

    private final SessionService sessionService;

    private final EventService eventService;

    private final LapService lapService;

    private final CrewConverter crewConverter;

    public EventResultServiceImpl(SessionService sessionService, EventService eventService, LapService lapService, CrewConverter crewConverter) {
        this.sessionService = sessionService;
        this.eventService = eventService;
        this.lapService = lapService;
        this.crewConverter = crewConverter;
    }

    @Override
    public EventResultDto getEventResultDto(Long eventId) {
        //@TODO: refactor this
        List<SessionEntity> eventSessions = sessionService.getSessionsByEventId(eventId);
        List<SessionEntity> competitionSessions = eventSessions.stream()
                .filter(session -> SessionType.COMPETITION.equals(session.getSessionType().getName()))
                .toList();

        List<CrewEntity> crews = eventService.getEventCrewRegistrations(eventId);

        int numberOfSessions = competitionSessions.size();
        Map<Integer, Long[]> lapTimes = new HashMap<>();
        crews.forEach(crew -> lapTimes.put(crew.getRacingNumber(), new Long[numberOfSessions + 1]));

        long[] worstTimes = new long[numberOfSessions];
        for (int i = 0; i < numberOfSessions; i++) {
            SessionEntity session = competitionSessions.get(i);
            List<CrewLapsDto> data = lapService.getLeaderboardBySessionId(session.getId());
            if (!data.isEmpty()) {
                long sessionWorstLapTime = data.getLast().getLapTimes().getFirst() + penalty;
                worstTimes[i] = sessionWorstLapTime;
                for (CrewLapsDto d: data) {
                    Long lapTime = d.getLapTimes().getFirst();
                    lapTimes.get(d.getCrew().getRacingNumber())[i] = lapTime;
                }
            }
        }

        for (Integer racingNumber: lapTimes.keySet()) {
            var laps = lapTimes.get(racingNumber);
            long summaryLapsTime = 0;
            for (int i = 0; i < laps.length - 1; i++) {
                long time = nonNull(laps[i]) ? laps[i] : worstTimes[i];
                summaryLapsTime += time;
            }
            laps[laps.length - 1] = summaryLapsTime;
        }

        List<CrewResultDto> cr = new ArrayList<>();
        for (CrewEntity crew: crews) {
            Long[] l = lapTimes.get(crew.getRacingNumber());
            CrewResultDto r = new CrewResultDto();
            var c = crewConverter.toCrewDto(crew);
            r.setCrew(c);
            r.setSummaryTime(l[l.length - 1]);
            List<Long> ct = new ArrayList<>(l.length);
            for (int i = 0; i < l.length - 1; i++) {
                ct.add(l[i]);
            }
            r.setCompetitionTimes(ct);
            cr.add(r);
        }
        Collections.sort(cr);


        EventResultDto evr = new EventResultDto(cr);


        return evr;
    }

}
