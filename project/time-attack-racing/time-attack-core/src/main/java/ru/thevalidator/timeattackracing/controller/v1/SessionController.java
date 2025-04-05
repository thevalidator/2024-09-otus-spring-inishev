package ru.thevalidator.timeattackracing.controller.v1;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.thevalidator.timeattackracing.converter.SessionConverter;
import ru.thevalidator.timeattackracing.dto.CreateSessionRequest;
import ru.thevalidator.timeattackracing.dto.CrewLapsDto;
import ru.thevalidator.timeattackracing.dto.EventSessionDto;
import ru.thevalidator.timeattackracing.dto.GroupedCrewLapsDto;
import ru.thevalidator.timeattackracing.dto.LapsSaveResult;
import ru.thevalidator.timeattackracing.dto.SessionResultByCategoryDto;
import ru.thevalidator.timeattackracing.entity.SessionEntity;
import ru.thevalidator.timeattackracing.entity.SessionTypeEntity;
import ru.thevalidator.timeattackracing.service.LapService;
import ru.thevalidator.timeattackracing.service.SessionService;
import ru.thevalidator.timeattackracing.service.filereader.FileType;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SessionController {

    private final SessionService sessionService;

    private final SessionConverter sessionConverter;

    private final LapService lapService;

    public SessionController(SessionService sessionService,
                             SessionConverter sessionConverter,
                             LapService lapService) {
        this.sessionService = sessionService;
        this.sessionConverter = sessionConverter;
        this.lapService = lapService;
    }

    @GetMapping("/sessions/types")
    public List<SessionTypeEntity> getAllSessionTypes() {
        return sessionService.getAllSessionTypes();
    }

    @PreAuthorize("hasAuthority('SCOPE_CREATE_SESSIONS')")
    @PostMapping("/events/{event_id}/sessions")
    public EventSessionDto createSession(@PathVariable(name = "event_id") Long eventId,
                                         @Valid @RequestBody CreateSessionRequest rq) {
        SessionEntity session = sessionService.createEventSession(eventId, rq);
        return sessionConverter.toEventSessionDto(session);
    }

    @GetMapping("/events/{event_id}/sessions")
    public List<EventSessionDto> getSessionsByEvent(@PathVariable(name = "event_id") Long eventId) {
        List<SessionEntity> sessions = sessionService.getSessionsByEventId(eventId);
        return sessions.stream().map(sessionConverter::toEventSessionDto).toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_UPLOAD_LAPS')")
    @PostMapping("/sessions/{session_id}/laps")
    public LapsSaveResult uploadSessionLaps(@PathVariable(name = "session_id") Long sessionId,
                                            @RequestParam FileType fileType,
                                            @RequestParam MultipartFile file) {
        return lapService.saveLaps(sessionId, fileType, file);
    }

    @GetMapping("/sessions/{session_id}/leaderboards")
    public List<CrewLapsDto> getAbsoluteSessionLeaderboard(@PathVariable(name = "session_id") Long sessionId) {
        return lapService.getLeaderboardBySessionId(sessionId);
    }

    @GetMapping("/sessions/{session_id}/leaderboards-grouped-by-category")
    public List<SessionResultByCategoryDto> getGroupedByCategorySessionLeaderboard(@PathVariable(name = "session_id") Long sessionId) {
        return lapService.getGroupedLeaderboardBySessionId(sessionId);
    }

    @PreAuthorize("hasAuthority('SCOPE_READ_ALL_LAPS')")
    @GetMapping("/sessions/{session_id}/laps")
    public GroupedCrewLapsDto getSessionLaps(@PathVariable(name = "session_id") Long sessionId) {
        return lapService.getCrewGroupedLapsBySessionId(sessionId);
    }

    @PostAuthorize("hasAuthority('SCOPE_READ_ALL_LAPS') or returnObject.crew.id.toString() == authentication.name")
    @GetMapping(value = "/sessions/{session_id}/laps", params = "racingNumber")
    public CrewLapsDto getCrewSessionLaps(@PathVariable(name = "session_id") Long sessionId,
                                          @RequestParam Integer racingNumber) {
        return lapService.getCrewLapsBySessionId(sessionId, racingNumber);
    }

}
