package ru.thevalidator.timeattackracing.converter;

import org.springframework.stereotype.Component;
import ru.thevalidator.timeattackracing.dto.EventSessionDto;
import ru.thevalidator.timeattackracing.entity.SessionEntity;

@Component
public class SessionConverter {

    public EventSessionDto toEventSessionDto(SessionEntity session) {
        EventSessionDto dto = new EventSessionDto();
        dto.setSessionId(session.getId());
        dto.setOrdinalNumber(session.getOrdinalNumber());
        dto.setSessionType(session.getSessionType());
        dto.setSessionName(session.getSessionName());
        dto.setLapsLimit(session.getLapsLimit());
        return dto;
    }

}
