package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.dao.dto.QuestionDto;

import static java.util.Objects.nonNull;

@Service
public class QuestionDtoVerifierImpl implements QuestionDtoVerifier {

    @Override
    public boolean verifyBean(QuestionDto dto) {
        return nonNull(dto) && nonNull(dto.getText()) && nonNull(dto.getAnswers());
    }

}
