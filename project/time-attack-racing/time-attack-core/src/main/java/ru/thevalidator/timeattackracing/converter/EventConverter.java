package ru.thevalidator.timeattackracing.converter;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import ru.thevalidator.timeattackracing.dto.EventDto;
import ru.thevalidator.timeattackracing.dto.EventPage;
import ru.thevalidator.timeattackracing.dto.Pagination;
import ru.thevalidator.timeattackracing.dto.TrackDto;
import ru.thevalidator.timeattackracing.entity.EventEntity;

@Component
public class EventConverter {

    private final TrackConverter trackConverter;

    public EventConverter(TrackConverter trackConverter) {
        this.trackConverter = trackConverter;
    }

    public EventDto toEventDto(EventEntity event) {
        TrackDto track = trackConverter.toTrackDto(event.getTrack());
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setDate(event.getDate());
        dto.setEventName(event.getName());
        dto.setTrack(track);
        dto.setCategories(event.getCategories());
        return dto;
    }

    public EventPage toEventPage(Slice<EventEntity> events, int offset) {
        EventPage page = new EventPage();
        var eventDtos = events.getContent().stream().map(this::toEventDto).toList();
        page.setEvents(eventDtos);

        Pagination pagination = new Pagination();
        pagination.setOffset(offset);
        pagination.setPageSizeLimit(events.getSize());
        pagination.setElements(events.getNumberOfElements());
        pagination.setHasPrevious(events.hasPrevious());
        pagination.setHasNext(events.hasNext());
        page.setPagination(pagination);

        return page;
    }

}
