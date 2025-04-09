package ru.thevalidator.timeattackracing.service;

import ru.thevalidator.timeattackracing.dto.CreateSessionRequest;
import ru.thevalidator.timeattackracing.entity.SessionEntity;
import ru.thevalidator.timeattackracing.entity.SessionTypeEntity;

import java.util.List;

public interface SessionService {

    List<SessionTypeEntity> getAllSessionTypes();

    SessionEntity createEventSession(long eventId, CreateSessionRequest rq);

    List<SessionEntity> getSessionsByEventId(long eventId);

    SessionEntity getSessionsById(long sessionId);

}
