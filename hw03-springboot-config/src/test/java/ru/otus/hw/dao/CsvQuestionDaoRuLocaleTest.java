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
@TestPropertySource(properties = {"test.locale=ru-RU"})
class CsvQuestionDaoRuLocaleTest {

    @Autowired
    private CsvQuestionDao dao;

    @Test
    void findAll() {
        List<Question> questionList = dao.findAll();

        Assertions.assertNotNull(questionList);
        assertEquals(2, questionList.size(), "Question list size should be 2");

        Question firstQuestion = questionList.get(0);
        Question secondQuestion = questionList.get(1);
        assertEquals("Вопрос 1?", firstQuestion.text());
        assertEquals(2, firstQuestion.answers().size());
        assertEquals("Вопрос 2?", secondQuestion.text());
        assertEquals(3, secondQuestion.answers().size());

        Answer firstAnswerOfSecondQuestion = secondQuestion.answers().get(0);
        assertEquals("Ответ 1", firstAnswerOfSecondQuestion.text());
        assertFalse(firstAnswerOfSecondQuestion.isCorrect());

        Answer secondAnswerOfSecondQuestion = secondQuestion.answers().get(1);
        assertEquals("Ответ 2", secondAnswerOfSecondQuestion.text());
        assertFalse(secondAnswerOfSecondQuestion.isCorrect());

        Answer thirdAnswerOfSecondQuestion = secondQuestion.answers().get(2);
        assertEquals("Ответ 3", thirdAnswerOfSecondQuestion.text());
        assertTrue(thirdAnswerOfSecondQuestion.isCorrect());
    }

}