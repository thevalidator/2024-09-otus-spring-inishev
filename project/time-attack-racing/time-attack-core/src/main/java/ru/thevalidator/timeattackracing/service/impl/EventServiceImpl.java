package ru.thevalidator.timeattackracing.service.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thevalidator.timeattackracing.dto.CreateEventRequest;
import ru.thevalidator.timeattackracing.dto.EventRegistrationRequest;
import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;
import ru.thevalidator.timeattackracing.entity.CrewEntity;
import ru.thevalidator.timeattackracing.entity.EventEntity;
import ru.thevalidator.timeattackracing.entity.TrackEntity;
import ru.thevalidator.timeattackracing.entity.UserEntity;
import ru.thevalidator.timeattackracing.entity.VehicleEntity;
import ru.thevalidator.timeattackracing.exception.ConstraintViolationErrorException;
import ru.thevalidator.timeattackracing.exception.ItemNotFoundException;
import ru.thevalidator.timeattackracing.repository.CrewRepository;
import ru.thevalidator.timeattackracing.repository.EventRepository;
import ru.thevalidator.timeattackracing.service.ClassificationCategoryService;
import ru.thevalidator.timeattackracing.service.EventService;
import ru.thevalidator.timeattackracing.service.TrackService;
import ru.thevalidator.timeattackracing.service.UserService;
import ru.thevalidator.timeattackracing.service.VehicleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final CrewRepository crewRepository;

    private final ClassificationCategoryService classificationCategoryService;

    private final UserService userService;

    private final VehicleService vehicleService;

    private final TrackService trackService;

    public EventServiceImpl(EventRepository eventRepository,
                            CrewRepository crewRepository,
                            ClassificationCategoryService classificationCategoryService,
                            UserService userService,
                            VehicleService vehicleService,
                            TrackService trackService) {
        this.eventRepository = eventRepository;
        this.crewRepository = crewRepository;
        this.classificationCategoryService = classificationCategoryService;
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.trackService = trackService;
    }

    @Override
    public EventEntity getById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Event not found [id=%s].", eventId)));
    }

    @Override
    public EventEntity createEvent(CreateEventRequest rq) {
        TrackEntity track = trackService.getTrackById(rq.getTrackId());
        List<ClassificationCategoryEntity> categories = classificationCategoryService.getAllCategoriesAreIn(rq.getCategoryIds());
        EventEntity event = new EventEntity();
        event.setName(rq.getName());
        event.setDate(rq.getDate());
        event.setTrack(track);
        event.setCategories(categories);
        return eventRepository.save(event);
    }

    @Override
    public Slice<EventEntity> getAllEvents(int offset, int pageSize) {
        int pageNumber = offset / pageSize;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "date"));
        return eventRepository.findAll(pageable);
    }

    @Override
    public void registerCrew(Long eventId, EventRegistrationRequest rq) {
        EventEntity event = getById(eventId);
        UserEntity user = userService.getUserById(rq.getUserId());
        VehicleEntity vehicle = vehicleService.getById(rq.getVehicleId());
        if (user.getId() != vehicle.getUser().getId()) {
            throw new ItemNotFoundException("No such vehicle found for user");
        }
        ClassificationCategoryEntity category = classificationCategoryService.getById(rq.getCategoryId());
        boolean hasCategory = event.getCategories().stream()
                .anyMatch(c -> Objects.equals(c.getId(), category.getId()));
        if (!hasCategory) {
            throw new ItemNotFoundException("No such category found for event");
        }

        CrewEntity registration = new CrewEntity();
        registration.setVehicle(vehicle);
        registration.setUser(user);
        registration.setCategory(category);
        registration.setEvent(event);
        registration.setRacingNumber(rq.getRacingNumber());
        registration.setCreatedAt(LocalDateTime.now());

        try {
            crewRepository.saveAndFlush(registration);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationErrorException("Duplicate user or race number");
        }
    }

    @Override
    public List<CrewEntity> getEventCrewRegistrations(Long eventId) {
        return crewRepository.findAllByEventId(eventId);
    }

}
