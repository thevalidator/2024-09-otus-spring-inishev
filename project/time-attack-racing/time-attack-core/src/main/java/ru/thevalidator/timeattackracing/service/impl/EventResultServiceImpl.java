package ru.thevalidator.timeattackracing.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thevalidator.timeattackracing.converter.ClassificationCategoryConverter;
import ru.thevalidator.timeattackracing.converter.CrewConverter;
import ru.thevalidator.timeattackracing.dto.ClassificationCategoryDto;
import ru.thevalidator.timeattackracing.dto.CrewLapsDto;
import ru.thevalidator.timeattackracing.dto.CrewResultDto;
import ru.thevalidator.timeattackracing.dto.EventResultByCategoryDto;
import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;
import ru.thevalidator.timeattackracing.entity.CrewEntity;
import ru.thevalidator.timeattackracing.entity.EventEntity;
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

    private final ClassificationCategoryConverter classificationCategoryConverter;

    public EventResultServiceImpl(SessionService sessionService,
                                  EventService eventService,
                                  LapService lapService,
                                  CrewConverter crewConverter,
                                  ClassificationCategoryConverter classificationCategoryConverter) {
        this.sessionService = sessionService;
        this.eventService = eventService;
        this.lapService = lapService;
        this.crewConverter = crewConverter;
        this.classificationCategoryConverter = classificationCategoryConverter;
    }

    @Override
    public List<EventResultByCategoryDto> getEventResultDto(Long eventId) {
        //@TODO: refactor this

        //get event categories
        EventEntity event = eventService.getById(eventId);
        List<ClassificationCategoryEntity> categories = event.getCategories();

        //prepare final result template
        Map<ClassificationCategoryEntity, EventResultByCategoryDto> finalResults = new HashMap<>();
        for (ClassificationCategoryEntity cat: categories) {
            EventResultByCategoryDto catResult = new EventResultByCategoryDto();

            ClassificationCategoryDto catDto = classificationCategoryConverter.toClassificationCategoryDto(cat);
            catResult.setClassificationCategory(catDto);
            catResult.setData(new ArrayList<>());
            finalResults.put(cat, catResult);
        }

        //get competition sessions
        List<SessionEntity> eventSessions = sessionService.getSessionsByEventId(eventId);
        List<SessionEntity> competitionSessions = eventSessions.stream()
                .filter(session -> SessionType.COMPETITION.equals(session.getSessionType().getName()))
                .toList();

        //get crews
        List<CrewEntity> crews = eventService.getEventCrewRegistrations(eventId);


        //collect lap times by crew
        Map<Integer, Long[]> lapTimes = new HashMap<>();
        int numberOfSessions = competitionSessions.size();
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


        for (CrewEntity crew: crews) {
            EventResultByCategoryDto classificationCategoryResults = finalResults.get(crew.getCategory());

            Long[] l = lapTimes.get(crew.getRacingNumber());
            CrewResultDto crewResult = new CrewResultDto();
            var c = crewConverter.toCrewDto(crew);
            crewResult.setCrew(c);
            crewResult.setSummaryTime(l[l.length - 1]);
            List<Long> ct = new ArrayList<>(l.length);
            for (int i = 0; i < l.length - 1; i++) {
                ct.add(l[i]);
            }
            crewResult.setCompetitionTimes(ct);

            classificationCategoryResults.getData().add(crewResult);
        }

        for (EventResultByCategoryDto dto: finalResults.values()) {
            Collections.sort(dto.getData());
        }

        return finalResults.values().stream().sorted().toList();
    }

}
