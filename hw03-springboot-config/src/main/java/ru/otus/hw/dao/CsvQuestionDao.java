package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.service.QuestionDtoVerifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;

    private final QuestionDtoVerifier questionDtoVerifier;

    @Override
    public List<Question> findAll() {
        try (InputStream is = getFileFromResourceAsStream(fileNameProvider.getTestFileName());
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            List<QuestionDto> questions = new CsvToBeanBuilder<QuestionDto>(isr)
                    .withSkipLines(1)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .withVerifier(questionDtoVerifier)
                    .build()
                    .parse();
            List<Question> questionDtoList = questions.stream().map(QuestionDto::toDomainObject).toList();
            if (questionDtoList.isEmpty()) {
                throw new QuestionReadException("no questions found");
            }
            return questionDtoList;
        } catch (IOException e) {
            throw new QuestionReadException(e.getMessage(), e);
        }
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (isNull(inputStream)) {
            throw new QuestionReadException("file not found: " + fileName);
        } else {
            return inputStream;
        }
    }

}
