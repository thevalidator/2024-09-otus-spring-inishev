package ru.thevalidator.timeattackracing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thevalidator.timeattackracing.dto.CreateSessionRequest;
import ru.thevalidator.timeattackracing.entity.EventEntity;
import ru.thevalidator.timeattackracing.entity.SessionEntity;
import ru.thevalidator.timeattackracing.entity.SessionTypeEntity;
import ru.thevalidator.timeattackracing.exception.ItemNotFoundException;
import ru.thevalidator.timeattackracing.repository.SessionRepository;
import ru.thevalidator.timeattackracing.repository.SessionTypeRepository;
import ru.thevalidator.timeattackracing.service.EventService;
import ru.thevalidator.timeattackracing.service.SessionService;

import java.util.List;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

    private final SessionTypeRepository sessionTypeRepository;

    private final EventService eventService;

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionTypeRepository sessionTypeRepository,
                              EventService eventService,
                              SessionRepository sessionRepository) {
        this.sessionTypeRepository = sessionTypeRepository;
        this.eventService = eventService;
        this.sessionRepository = sessionRepository;
    }


    @Override
    public List<SessionTypeEntity> getAllSessionTypes() {
        return sessionTypeRepository.findAll();
    }

    @Override
    public SessionEntity createEventSession(long eventId, CreateSessionRequest rq) {
        SessionEntity session = new SessionEntity();
        EventEntity event = eventService.getById(eventId);
        session.setEvent(event);
        session.setSessionType(sessionTypeRepository.findById(rq.getTypeId())
                .orElseThrow(() -> new ItemNotFoundException("No such session type found")));
        List<SessionEntity> eventSessions = sessionRepository.findAllByEvent(event);
        int sessionOrdinalNumber = eventSessions.size() + 1;
        session.setOrdinalNumber(sessionOrdinalNumber);
        session.setSessionName(rq.getSessionName());
        session.setLapsLimit(rq.getLapsLimit());

        session = sessionRepository.save(session);
        log.info("Session created [id={}]", session.getId());
        return session;
    }

    @Override
    public List<SessionEntity> getSessionsByEventId(long eventId) {
        return sessionRepository.findAllByEventId(eventId, Sort.by(Sort.Direction.ASC, "ordinalNumber"));
    }

    @Override
    public SessionEntity getSessionsById(long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ItemNotFoundException("No such session found"));
    }

}
