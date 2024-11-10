package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.otus.hw.Application;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = Application.class, initializers = ConfigDataApplicationContextInitializer.class)
@TestPropertySource(properties = {"test.locale=en-US"})
class CsvQuestionDaoTest {

    @Autowired
    private CsvQuestionDao dao;

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