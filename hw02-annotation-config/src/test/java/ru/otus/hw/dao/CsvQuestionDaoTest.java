package ru.otus.hw.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.Application;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
class CsvQuestionDaoTest {

    private final CsvQuestionDao dao;

    @Autowired
    CsvQuestionDaoTest(CsvQuestionDao dao) {
        this.dao = dao;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
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

}