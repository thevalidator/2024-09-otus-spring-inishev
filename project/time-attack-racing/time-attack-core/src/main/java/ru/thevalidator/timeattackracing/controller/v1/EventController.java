package ru.thevalidator.timeattackracing.controller.v1;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.thevalidator.timeattackracing.converter.CrewConverter;
import ru.thevalidator.timeattackracing.converter.EventConverter;
import ru.thevalidator.timeattackracing.dto.CreateEventRequest;
import ru.thevalidator.timeattackracing.dto.EventDto;
import ru.thevalidator.timeattackracing.dto.EventPage;
import ru.thevalidator.timeattackracing.dto.EventRegistrationRequest;
import ru.thevalidator.timeattackracing.dto.EventResultDto;
import ru.thevalidator.timeattackracing.dto.GroupedCrewListDto;
import ru.thevalidator.timeattackracing.service.EventResultService;
import ru.thevalidator.timeattackracing.service.EventService;


@RestController
@RequestMapping("/api/v1")
public class EventController {

    private final EventService eventService;

    private final EventConverter eventConverter;

    private final CrewConverter crewConverter;

    private final EventResultService eventResultService;


    public EventController(EventService eventService,
                           EventConverter eventConverter,
                           CrewConverter crewConverter,
                           EventResultService eventResultService) {
        this.eventService = eventService;
        this.eventConverter = eventConverter;
        this.crewConverter = crewConverter;
        this.eventResultService = eventResultService;
    }

    @PreAuthorize("hasAuthority('SCOPE_CREATE_EVENTS')")
    @PostMapping("/events")
    public EventDto createEvent(@Valid @RequestBody CreateEventRequest rq) {
        var event = eventService.createEvent(rq);
        return eventConverter.toEventDto(event);
    }

    @GetMapping("/events")
    public EventPage getAllEvent(@RequestParam(required = false, defaultValue = "0") int offset,
                                 @RequestParam(required = false, defaultValue = "10") int size) {
        var events = eventService.getAllEvents(offset, size);
        return eventConverter.toEventPage(events, offset);
    }

    @GetMapping("/events/{event_id}")
    public EventDto getEventById(@PathVariable("event_id") Long eventId) {
        var event = eventService.getById(eventId);
        return eventConverter.toEventDto(event);
    }

    @PreAuthorize("hasAuthority('SCOPE_CREATE_REGISTRATIONS') or #rq.userId.toString() == authentication.name")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events/{event_id}/crews")
    public void registerInEvent(@PathVariable("event_id") Long eventId,
                                @Valid @RequestBody EventRegistrationRequest rq) {
        eventService.registerCrew(eventId, rq);
    }

    @PreAuthorize("hasAuthority('SCOPE_READ_REGISTRATIONS')")
    @GetMapping("/events/{event_id}/crews")
    public GroupedCrewListDto getEventRegistrations(@PathVariable("event_id") Long eventId) {
        var registrations = eventService.getEventCrewRegistrations(eventId);
        return crewConverter.toEventCategoryCrewDto(registrations);
    }

    @GetMapping("/events/{event_id}/results")
    public EventResultDto getEventResults(@PathVariable("event_id") Long eventId) {
        return eventResultService.getEventResultDto(eventId);
    }

}
