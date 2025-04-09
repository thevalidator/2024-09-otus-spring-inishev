package ru.thevalidator.timeattackracing.service.filereader.impl;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.thevalidator.timeattackracing.converter.LapConverter;
import ru.thevalidator.timeattackracing.dto.LapDto;
import ru.thevalidator.timeattackracing.dto.LapsReadResult;
import ru.thevalidator.timeattackracing.entity.SessionEntity;
import ru.thevalidator.timeattackracing.exception.LapFileParsingException;
import ru.thevalidator.timeattackracing.service.filereader.FileType;
import ru.thevalidator.timeattackracing.service.filereader.LapsFileReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.thevalidator.timeattackracing.service.filereader.impl.CsvLapsFileReaderImpl.HEADERS.LAP_TIME;
import static ru.thevalidator.timeattackracing.service.filereader.impl.CsvLapsFileReaderImpl.HEADERS.RACING_NUMBER;

@Component
public class CsvLapsFileReaderImpl implements LapsFileReader {

    private static final Logger log = LoggerFactory.getLogger(CsvLapsFileReaderImpl.class);

    private final LapConverter lapConverter;

    private final Map<HEADERS, Integer> positions = Map.ofEntries(
            Map.entry(RACING_NUMBER, 1),
            Map.entry(LAP_TIME, 2)
    );

    private final String LAP_TIME_PATTERN = "^(?<minutes>\\d{1,2}):(?<seconds>\\d{2}).(?<millis>\\d{3})$";

    private final Pattern PATTERN = Pattern.compile(LAP_TIME_PATTERN, Pattern.UNICODE_CHARACTER_CLASS);

    public CsvLapsFileReaderImpl(LapConverter lapConverter) {
        this.lapConverter = lapConverter;
    }

    @Override
    public LapsReadResult readLaps(MultipartFile file, SessionEntity session, Set<Integer> raceNumbers) {
        List<LapDto> readLaps = new ArrayList<>();
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        int totalRecords = 0;
        AtomicInteger badRecords = new AtomicInteger();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .withSkipLines(1)
                .withCSVParser(csvParser)
                .build()) {

            List<String[]> elements = reader.readAll();
            totalRecords = elements.size();
            elements.forEach(e -> {
                int racingNumber = Integer.parseInt(e[positions.get(RACING_NUMBER)]);
                if (!raceNumbers.contains(racingNumber)) {
                    log.error("Race number {} is not registered in the event id {}",
                            racingNumber, session.getEvent().getId());
                    badRecords.getAndIncrement();
                    return;
                }

                String lapTimeValue = e[positions.get(LAP_TIME)];
                Optional<Long> lapTimeOptional = parseLapTimeAsLong(lapTimeValue);
                if (lapTimeOptional.isEmpty()) {
                    badRecords.getAndIncrement();
                    return;
                }

                long lapTime = lapTimeOptional.get();
                LapDto lap = new LapDto();
                lap.setRacingNumber(racingNumber);
                lap.setLapTime(lapTime);
                readLaps.add(lap);
            });

        } catch (IOException | CsvException e) {
            throw new LapFileParsingException(e.getMessage());
        }

        LapsReadResult readResult = new LapsReadResult();
        var laps = readLaps.stream().map(l -> lapConverter.toLapEntity(l, session)).toList();
        readResult.setLaps(laps);
        readResult.setTotalRecords(totalRecords);
        readResult.setBadRecords(badRecords.get());
        return readResult;
    }

    private Optional<Long> parseLapTimeAsLong(String lapTimeValue) {
        Matcher matcher = PATTERN.matcher(lapTimeValue);
        if (matcher.find()) {
            int minutes = Integer.parseInt(matcher.group("minutes"));
            int seconds = Integer.parseInt(matcher.group("seconds"));
            long millis = Long.parseLong(matcher.group("millis"));
            return Optional.of(Duration.ofMinutes(minutes).plusSeconds(seconds).plusMillis(millis).toMillis());
        } else {
            log.error("Invalid lap time value: {}", lapTimeValue);
        }
        return Optional.empty();
    }

    @Override
    public FileType getFileType() {
        return FileType.CSV;
    }

    enum HEADERS {
        RACING_NUMBER,
        LAP_TIME
    }

}
