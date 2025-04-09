package ru.thevalidator.timeattackracing.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public class CreateEventRequest {

    @Size(max = 255)
    @NotEmpty(message = "Event's name can't be blank")
    private String name;

    @NotNull(message = "Missing parameter 'date'")
    private LocalDate date;

    @NotNull(message = "Missing parameter 'track_id'")
    @Min(1)
    private Long trackId;

    @NotEmpty(message = "Event's categories can't be empty")
    private Set<Integer> categoryIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public Set<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

}
