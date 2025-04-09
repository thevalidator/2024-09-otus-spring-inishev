package ru.thevalidator.timeattackracing.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.thevalidator.timeattackracing.serializer.LapTimeSerializer;
import ru.thevalidator.timeattackracing.serializer.LapTimesSerializer;

import java.util.List;

public class CrewResultDto implements Comparable<CrewResultDto> {

    private CrewDto crew;

    @JsonSerialize(using = LapTimesSerializer.class)
    private List<Long> competitionTimes;

    @JsonSerialize(using = LapTimeSerializer.class)
    private Long summaryTime;

    public CrewDto getCrew() {
        return crew;
    }

    public void setCrew(CrewDto crew) {
        this.crew = crew;
    }

    public List<Long> getCompetitionTimes() {
        return competitionTimes;
    }

    public void setCompetitionTimes(List<Long> competitionTimes) {
        this.competitionTimes = competitionTimes;
    }

    public Long getSummaryTime() {
        return summaryTime;
    }

    public void setSummaryTime(Long summaryTime) {
        this.summaryTime = summaryTime;
    }

    @Override
    public int compareTo(CrewResultDto o) {
        return this.summaryTime.compareTo(o.summaryTime);
    }

}
