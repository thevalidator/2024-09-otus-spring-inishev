package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.service.QuestionDtoVerifier;
import ru.otus.hw.service.QuestionDtoVerifierImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    private static final QuestionDtoVerifier questionDtoVerifier = new QuestionDtoVerifierImpl();

    @Mock
    TestFileNameProvider fileNameProvider;

    @Test
    void findAllShouldReturnQuestionsWithAnswers() {
        when(fileNameProvider.getTestFileName()).thenReturn("questions-test.csv");

        CsvQuestionDao dao = new CsvQuestionDao(fileNameProvider, questionDtoVerifier);
        List<Question> questionList = dao.findAll();

        Assertions.assertNotNull(questionList);
        assertEquals(2, questionList.size(), "Question list size should be 2");

        Question firstQuestion = questionList.get(0);
        Question secondQuestion = questionList.get(1);
        assertEquals("Question One?", firstQuestion.text());
        assertEquals(2, firstQuestion.answers().size());
        assertEquals("Question Two?", secondQuestion.text());
        assertEquals(3, secondQuestion.answers().size());

        Answer firstAnswerOfSecondQuestion = secondQuestion.answers().get(0);
        assertEquals("Answer one", firstAnswerOfSecondQuestion.text());
        assertFalse(firstAnswerOfSecondQuestion.isCorrect());

        Answer secondAnswerOfSecondQuestion = secondQuestion.answers().get(1);
        assertEquals("Answer two", secondAnswerOfSecondQuestion.text());
        assertFalse(secondAnswerOfSecondQuestion.isCorrect());

        Answer thirdAnswerOfSecondQuestion = secondQuestion.answers().get(2);
        assertEquals("Answer three", thirdAnswerOfSecondQuestion.text());
        assertTrue(thirdAnswerOfSecondQuestion.isCorrect());
    }

    @Test
    void whenFileDoesNotExistFindAllShouldThrowQuestionReadException() {
        when(fileNameProvider.getTestFileName()).thenReturn("not-existing-file.csv");
        CsvQuestionDao dao = new CsvQuestionDao(fileNameProvider, questionDtoVerifier);

        QuestionReadException ex = Assertions.assertThrows(QuestionReadException.class, dao::findAll);

        assertTrue(ex.getMessage().contains("file not found"));
    }

    @Test
    void whenFileContainsInvalidDataFindAllShouldThrowQuestionReadException() {
        when(fileNameProvider.getTestFileName()).thenReturn("invalid-questions-test.csv");
        CsvQuestionDao dao = new CsvQuestionDao(fileNameProvider, questionDtoVerifier);

        QuestionReadException ex = Assertions.assertThrows(QuestionReadException.class, dao::findAll);

        assertTrue(ex.getMessage().contains("no questions found"));
    }

}