package ru.thevalidator.timeattackracing.service;

import org.springframework.web.multipart.MultipartFile;
import ru.thevalidator.timeattackracing.dto.CrewLapsDto;
import ru.thevalidator.timeattackracing.dto.GroupedCrewLapsDto;
import ru.thevalidator.timeattackracing.dto.LapsSaveResult;
import ru.thevalidator.timeattackracing.service.filereader.FileType;

import java.util.List;

public interface LapService {

    LapsSaveResult saveLaps(Long sessionId, FileType fileType, MultipartFile file);

    GroupedCrewLapsDto getCrewGroupedLapsBySessionId(Long sessionId);

    List<CrewLapsDto> getLeaderboardBySessionId(Long sessionId);

    CrewLapsDto getCrewLapsBySessionId(Long sessionId, Integer racingNumber);

}
