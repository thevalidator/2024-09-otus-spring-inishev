package ru.thevalidator.timeattackracing.service;

import org.springframework.data.domain.Slice;
import ru.thevalidator.timeattackracing.dto.CreateEventRequest;
import ru.thevalidator.timeattackracing.dto.EventRegistrationRequest;
import ru.thevalidator.timeattackracing.entity.CrewEntity;
import ru.thevalidator.timeattackracing.entity.EventEntity;

import java.util.List;

public interface EventService {

    EventEntity getById(Long id);

    EventEntity createEvent(CreateEventRequest rq);

    Slice<EventEntity> getAllEvents(int offset, int pageSize);

    void registerCrew(Long eventId, EventRegistrationRequest rq);

    List<CrewEntity> getEventCrewRegistrations(Long eventId);

}
