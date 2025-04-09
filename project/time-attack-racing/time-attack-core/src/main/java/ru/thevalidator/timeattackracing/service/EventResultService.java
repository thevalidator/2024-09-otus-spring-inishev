package ru.thevalidator.timeattackracing.service;

import ru.thevalidator.timeattackracing.dto.EventResultByCategoryDto;

import java.util.List;

public interface EventResultService {

    List<EventResultByCategoryDto> getEventResultDto(Long eventId);

}
