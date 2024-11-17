package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.service.QuestionDtoVerifierImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CsvQuestionDao.class, QuestionDtoVerifierImpl.class})
class CsvQuestionDaoTest {

    @MockBean
    TestFileNameProvider fileNameProvider;
    @Autowired
    CsvQuestionDao csvQuestionDao;

    @Test
    void whenCorrectFileFindAllShouldReturnAllQuestionsWithAnswers() {
        when(fileNameProvider.getTestFileName()).thenReturn("questions-test.csv");

        List<Question> questionList = csvQuestionDao.findAll();

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

        QuestionReadException ex = Assertions.assertThrows(QuestionReadException.class, csvQuestionDao::findAll);

        assertTrue(ex.getMessage().contains("file not found"));
    }

    @Test
    void whenFileContainsInvalidDataFindAllShouldThrowQuestionReadException() {
        when(fileNameProvider.getTestFileName()).thenReturn("invalid-questions-test.csv");

        QuestionReadException ex = Assertions.assertThrows(QuestionReadException.class, csvQuestionDao::findAll);

        assertTrue(ex.getMessage().contains("no questions found"));
    }

}