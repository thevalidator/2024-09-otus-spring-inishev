package ru.thevalidator.timeattackracing.dto;

import java.util.List;

public class EventPage {

    private List<EventDto> events;

    private Pagination pagination;

    public List<EventDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventDto> events) {
        this.events = events;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
