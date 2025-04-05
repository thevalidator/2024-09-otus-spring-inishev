package ru.thevalidator.timeattackracing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.thevalidator.timeattackracing.converter.ClassificationCategoryConverter;
import ru.thevalidator.timeattackracing.converter.CrewConverter;
import ru.thevalidator.timeattackracing.dto.CrewDto;
import ru.thevalidator.timeattackracing.dto.CrewLapsDto;
import ru.thevalidator.timeattackracing.dto.GroupedCrewLapsDto;
import ru.thevalidator.timeattackracing.dto.LapsReadResult;
import ru.thevalidator.timeattackracing.dto.LapsSaveResult;
import ru.thevalidator.timeattackracing.dto.SessionResultByCategoryDto;
import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;
import ru.thevalidator.timeattackracing.entity.CrewEntity;
import ru.thevalidator.timeattackracing.entity.EventEntity;
import ru.thevalidator.timeattackracing.entity.LapEntity;
import ru.thevalidator.timeattackracing.entity.SessionEntity;
import ru.thevalidator.timeattackracing.exception.ItemNotFoundException;
import ru.thevalidator.timeattackracing.exception.UnsupportedFormatException;
import ru.thevalidator.timeattackracing.repository.CrewRepository;
import ru.thevalidator.timeattackracing.repository.LapRepository;
import ru.thevalidator.timeattackracing.service.LapService;
import ru.thevalidator.timeattackracing.service.SessionService;
import ru.thevalidator.timeattackracing.service.filereader.FileType;
import ru.thevalidator.timeattackracing.service.filereader.LapsFileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class LapServiceImpl implements LapService {

    private static final Logger log = LoggerFactory.getLogger(LapServiceImpl.class);

    private final List<LapsFileReader> fileReaders;

    private final SessionService sessionService;

    private final LapRepository lapRepository;

    private final CrewRepository crewRepository;

    private final CrewConverter crewConverter;

    private final ClassificationCategoryConverter classificationCategoryConverter;

    public LapServiceImpl(List<LapsFileReader> fileReaders,
                          SessionService sessionService,
                          LapRepository lapRepository,
                          CrewRepository crewRepository,
                          CrewConverter crewConverter,
                          ClassificationCategoryConverter classificationCategoryConverter) {
        this.fileReaders = fileReaders;
        this.sessionService = sessionService;
        this.lapRepository = lapRepository;
        this.crewRepository = crewRepository;
        this.crewConverter = crewConverter;
        this.classificationCategoryConverter = classificationCategoryConverter;
    }

    @Override
    public LapsSaveResult saveLaps(Long sessionId, FileType fileType, MultipartFile file) {
        log.info("Saving laps for session {} from file {} of type {}", sessionId, file.getOriginalFilename(), fileType);
        SessionEntity session = sessionService.getSessionsById(sessionId);
        Long eventId = session.getEvent().getId();
        Set<Integer> eventRacingNumbers = crewRepository.findAllByEventId(eventId).stream()
                .map(CrewEntity::getRacingNumber)
                .collect(Collectors.toSet());

        LapsFileReader reader = getFileLapsReader(fileType);
        LapsReadResult readResult = reader.readLaps(file, session, eventRacingNumbers);
        List<LapEntity> laps = readResult.getLaps();
        var savedLaps = lapRepository.saveAll(laps);

        int successful = savedLaps.size();
        int failed = readResult.getBadRecords();
        int total = readResult.getTotalRecords();
        log.info("Saved laps result: success - {}, fail - {}, total - {}", successful, failed, total);
        return new LapsSaveResult(successful, failed, total);
    }

    private LapsFileReader getFileLapsReader(FileType fileType) {
        return fileReaders.stream()
                .filter(reader -> reader.getFileType().equals(fileType))
                .findFirst()
                .orElseThrow(() -> new UnsupportedFormatException(String.format("File type %s is not supported yet",
                        fileType)));
    }

    @Override
    public GroupedCrewLapsDto getCrewGroupedLapsBySessionId(Long sessionId) {
        SessionEntity session = sessionService.getSessionsById(sessionId);
        List<CrewEntity> regs = crewRepository.findAllByEventId(session.getEvent().getId());

        Map<Integer, CrewDto> crews = new HashMap<>();
        regs.forEach(reg -> crews.put(reg.getRacingNumber(), crewConverter.toCrewDto(reg)));


        List<LapEntity> sessionLaps = lapRepository.findLapsBySessionId(sessionId);
        Map<Integer, List<Long>> racingNumberGroupedLaps = new HashMap<>();
        regs.forEach(reg -> racingNumberGroupedLaps.put(reg.getRacingNumber(), new ArrayList<>()));

        sessionLaps.forEach(lap -> {
            int racingNumber = lap.getRacingNumber();
            Long lapTime = lap.getLapTime();
            if (racingNumberGroupedLaps.containsKey(racingNumber)) {
                racingNumberGroupedLaps.get(racingNumber).add(lapTime);
            } else {
                log.error("Lap id '{}': race number '{}' in session id '{}' is not found",
                        lap.getId(), racingNumber, sessionId);
            }
        });

        List<CrewLapsDto> crewLaps = crews.keySet().stream()
                .map(k -> {
                    CrewLapsDto dto = new CrewLapsDto();
                    dto.setCrew(crews.get(k));
                    dto.setLapTimes(racingNumberGroupedLaps.get(k));
                    return dto;
                })
                .toList();

        GroupedCrewLapsDto data = new GroupedCrewLapsDto();
        data.setData(crewLaps);

        return data;
    }

    @Override
    public List<CrewLapsDto> getLeaderboardBySessionId(Long sessionId) {
        List<CrewLapsDto> leaderboard = new ArrayList<>();
        List<LapEntity> bestLaps = lapRepository.findAscSortedCrewBestLapsBySessionId(sessionId);
        SessionEntity session = sessionService.getSessionsById(sessionId);
        bestLaps.forEach(lap -> {
            Optional<CrewEntity> crew = crewRepository.findByRacingNumberAndEventId(lap.getRacingNumber(), session.getEvent().getId());
            if (crew.isPresent()) {
                CrewDto c = crewConverter.toCrewDto(crew.get());
                Long lapTime = lap.getLapTime();
                CrewLapsDto data = new CrewLapsDto();
                data.setCrew(c);
                data.setLapTimes(List.of(lapTime));
                leaderboard.add(data);
            }
        });
        return leaderboard;
    }

    @Override
    public List<SessionResultByCategoryDto> getGroupedLeaderboardBySessionId(Long sessionId) {
        List<LapEntity> bestLaps = lapRepository.findAscSortedCrewBestLapsBySessionId(sessionId);
        SessionEntity session = sessionService.getSessionsById(sessionId);
        EventEntity event = session.getEvent();
        List<ClassificationCategoryEntity> categories = event.getCategories();

        Map<ClassificationCategoryEntity, SessionResultByCategoryDto> leaderboardByCategory = new HashMap<>();
        for (ClassificationCategoryEntity c: categories) {
            SessionResultByCategoryDto result = new SessionResultByCategoryDto();
            result.setClassificationCategory(classificationCategoryConverter.toClassificationCategoryDto(c));
            result.setData(new ArrayList<>());
            leaderboardByCategory.put(c, result);
        }

        bestLaps.forEach(lap -> {
            Optional<CrewEntity> crew = crewRepository.findByRacingNumberAndEventId(lap.getRacingNumber(), event.getId());
            if (crew.isPresent()) {
                CrewEntity crewEntity = crew.get();
                CrewDto c = crewConverter.toCrewDto(crew.get());
                Long lapTime = lap.getLapTime();
                CrewLapsDto data = new CrewLapsDto();
                data.setCrew(c);
                data.setLapTimes(List.of(lapTime));
                leaderboardByCategory.get(crewEntity.getCategory()).getData().add(data);
            }
        });

        return leaderboardByCategory.values().stream().toList();
    }

    @Override
    public CrewLapsDto getCrewLapsBySessionId(Long sessionId, Integer racingNumber) {
        SessionEntity session = sessionService.getSessionsById(sessionId);
        Long eventId = session.getEvent().getId();
        CrewEntity crew = crewRepository.findByRacingNumberAndEventId(racingNumber, eventId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Crew with racing number '%s' not found",
                        racingNumber)));
        List<LapEntity> laps = lapRepository.findLapsBySessionIdAndRacingNumber(sessionId, racingNumber);

        CrewLapsDto dto = new CrewLapsDto();
        dto.setCrew(crewConverter.toCrewDto(crew));
        dto.setLapTimes(laps.stream().map(LapEntity::getLapTime).toList());

        return dto;
    }

}
