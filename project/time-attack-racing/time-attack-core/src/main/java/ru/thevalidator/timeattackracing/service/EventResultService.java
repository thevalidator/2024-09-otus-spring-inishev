package ru.thevalidator.timeattackracing.service;

import ru.thevalidator.timeattackracing.dto.EventResultDto;

public interface EventResultService {

    EventResultDto getEventResultDto(Long eventId);

}
