package ru.thevalidator.timeattackracing.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.thevalidator.timeattackracing.serializer.LapTimesSerializer;

import java.util.List;

public class CrewLapsDto {

    private CrewDto crew;

    @JsonSerialize(using = LapTimesSerializer.class)
    private List<Long> lapTimes;

    public CrewDto getCrew() {
        return crew;
    }

    public void setCrew(CrewDto crew) {
        this.crew = crew;
    }

    public List<Long> getLapTimes() {
        return lapTimes;
    }

    public void setLapTimes(List<Long> lapTimes) {
        this.lapTimes = lapTimes;
    }

}
