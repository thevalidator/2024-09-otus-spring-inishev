package ru.thevalidator.timeattackracing.service.filereader;

import org.springframework.web.multipart.MultipartFile;
import ru.thevalidator.timeattackracing.dto.LapsReadResult;
import ru.thevalidator.timeattackracing.entity.SessionEntity;

import java.util.Set;

public interface LapsFileReader {

    LapsReadResult readLaps(MultipartFile file, SessionEntity session, Set<Integer> raceNumbers);

    FileType getFileType();

}
