package ru.thevalidator.timeattackracing.service.filereader.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import ru.thevalidator.timeattackracing.converter.LapConverter;
import ru.thevalidator.timeattackracing.dto.LapsReadResult;
import ru.thevalidator.timeattackracing.entity.EventEntity;
import ru.thevalidator.timeattackracing.entity.SessionEntity;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CsvLapsFileReaderImplTest {

    private CsvLapsFileReaderImpl csvLapsFileReader;

    @BeforeEach
    void setUp() {
        csvLapsFileReader = new CsvLapsFileReaderImpl(new LapConverter());
    }

    @Test
    void readLaps() {
        MockMultipartFile file = getMockMultipartFile();
        Set<Integer> raceNumbers = Set.of(33, 2);
        long eventId = 3L;
        SessionEntity session = getSession(eventId);

        LapsReadResult read = csvLapsFileReader.readLaps(file, session, raceNumbers);

        assertThat(read.getLaps()).as("Successfully read laps").hasSize(3);
        assertThat(read.getLaps()).as("All laps have correct event id")
                .allMatch(lap -> lap.getSession().getEvent().getId() == eventId);
        assertThat(read.getLaps()).filteredOn(lap -> lap.getRacingNumber() == 33).hasSize(1);
        assertThat(read.getLaps()).filteredOn(lap -> lap.getRacingNumber() == 2).hasSize(2);
        assertThat(read.getBadRecords()).as("Failed read laps").isEqualTo(3);
        assertThat(read.getTotalRecords()).as("Total read laps").isEqualTo(6);

    }

    private static SessionEntity getSession(long eventId) {
        EventEntity event = new EventEntity();
        event.setId(eventId);
        SessionEntity session = new SessionEntity();
        session.setEvent(event);
        return session;
    }

    private static MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile(
                "laps-data",
                ("""
                        Время дня;Номер экипажа;Время круга;Скорость
                        14:27:15.765;33;1:51.865;69,191
                        14:27:15.765;21;1:51.865;69,191
                        14:27:15.765;1;1:516.865;69,191
                        14:27:15.765;1;1:51f.865;69,191
                        14:29:07.487;2;1:51.722;69,279
                        14:29:07.487;2;1:51.822;69,279""").getBytes(StandardCharsets.UTF_8)
        );
    }

}