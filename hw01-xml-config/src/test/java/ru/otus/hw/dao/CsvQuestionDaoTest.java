package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvQuestionDaoTest {

    private static CsvQuestionDao questionDao;

    @BeforeAll
    static void beforeAll() {
        questionDao = new CsvQuestionDao(() -> "questions-test.csv");
    }

    @Test
    void findAllTest() {
        List<Question> questionList = questionDao.findAll();

        Assertions.assertNotNull(questionList);
        assertEquals(2, questionList.size(), "Question list size should be 2");

        Question firstQuestion = questionList.get(0);
        Question secondQuestion = questionList.get(1);
        assertEquals("Question One?", firstQuestion.text());
        assertEquals(2, firstQuestion.answers().size());
        assertEquals("Question Two?", secondQuestion.text());
        assertEquals(3, secondQuestion.answers().size());

        Answer thirdAnswerOfSecondQuestion = secondQuestion.answers().get(2);
        assertEquals("Answer three", thirdAnswerOfSecondQuestion.text());
        assertTrue(thirdAnswerOfSecondQuestion.isCorrect());
    }

}