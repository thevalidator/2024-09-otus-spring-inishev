package ru.thevalidator.timeattackracing.dto;

import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;

import java.time.LocalDate;
import java.util.List;

public class EventDto {

    private Long id;

    private LocalDate date;

    private String eventName;

    private TrackDto track;

    private List<ClassificationCategoryEntity> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public TrackDto getTrack() {
        return track;
    }

    public void setTrack(TrackDto track) {
        this.track = track;
    }

    public List<ClassificationCategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<ClassificationCategoryEntity> categories) {
        this.categories = categories;
    }

}
