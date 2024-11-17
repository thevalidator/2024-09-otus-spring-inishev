package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static ru.otus.hw.service.StudentServiceImplTest.STUDENT_LASTNAME;
import static ru.otus.hw.service.StudentServiceImplTest.STUDENT_NAME;

@SpringBootTest(classes = TestServiceImpl.class)
class TestServiceImplTest {

    private static final Student student = new Student(STUDENT_NAME, STUDENT_LASTNAME);

    @MockBean
    private LocalizedIOService ioServiceMock;
    @MockBean
    private QuestionDao questionDaoMock;
    @Autowired
    private TestService testService;

    @Test
    void testResultContainsCorrectStudent() {
        TestResult result = testService.executeTestFor(student);
        assertEquals(STUDENT_NAME, result.getStudent().firstName());
        assertEquals(STUDENT_LASTNAME, result.getStudent().lastName());
    }

    @Test
    void testStudentWasAskedAllQuestions() {
        List<Question> questions = getQuestions();

        when(questionDaoMock.findAll()).thenReturn(questions);
        when(ioServiceMock.readIntForRangeWithPromptLocalized(eq(1),
                anyInt(),
                eq("TestService.choose.correct.option"),
                eq("TestService.no.such.answer")))
                .thenReturn(1);

        TestResult result = testService.executeTestFor(student);
        assertEquals(questions.size(), result.getAnsweredQuestions().size());
        assertTrue(result.getAnsweredQuestions().containsAll(questions));
    }

    @Test
    void testStudentAnsweredCorrectAllQuestions() {
        List<Question> questions = getQuestions();
        int expectedNumberOfCorrectAnswers = questions.size();

        when(questionDaoMock.findAll()).thenReturn(questions);
        when(ioServiceMock.readIntForRangeWithPromptLocalized(1,
                3,
                "TestService.choose.correct.option",
                "TestService.no.such.answer"))
                .thenReturn(3);
        when(ioServiceMock.readIntForRangeWithPromptLocalized(1,
                2,
                "TestService.choose.correct.option",
                "TestService.no.such.answer"))
                .thenReturn(1);

        TestResult result = testService.executeTestFor(student);
        assertEquals(expectedNumberOfCorrectAnswers, result.getRightAnswersCount());
    }

    @Test
    void testStudentAnsweredCorrectOneQuestion() {
        List<Question> questions = getQuestions();
        int expectedNumberOfCorrectAnswers = 1;

        when(questionDaoMock.findAll()).thenReturn(questions);
        when(ioServiceMock.readIntForRangeWithPromptLocalized(1,
                3,
                "TestService.choose.correct.option",
                "TestService.no.such.answer"))
                .thenReturn(3);
        when(ioServiceMock.readIntForRangeWithPromptLocalized(1,
                2,
                "TestService.choose.correct.option",
                "TestService.no.such.answer"))
                .thenReturn(2);

        TestResult result = testService.executeTestFor(student);
        assertEquals(expectedNumberOfCorrectAnswers, result.getRightAnswersCount());
    }

    @Test
    void testStudentAnsweredInCorrectAllQuestions() {
        List<Question> questions = getQuestions();
        int expectedNumberOfCorrectAnswers = 0;

        when(questionDaoMock.findAll()).thenReturn(questions);
        when(ioServiceMock.readIntForRangeWithPromptLocalized(1,
                3,
                "TestService.choose.correct.option",
                "TestService.no.such.answer"))
                .thenReturn(2);
        when(ioServiceMock.readIntForRangeWithPromptLocalized(1,
                2,
                "TestService.choose.correct.option",
                "TestService.no.such.answer"))
                .thenReturn(2);

        TestResult result = testService.executeTestFor(student);
        assertEquals(expectedNumberOfCorrectAnswers, result.getRightAnswersCount());
    }

    @Test
    void testStudentAnsweredCorrectOnQuestionWithoutAnswers() {
        List<Question> questions = getQuestionWithNoAnswers();
        int expectedNumberOfCorrectAnswers = questions.size();

        when(questionDaoMock.findAll()).thenReturn(questions);
        when(ioServiceMock.readStringWithPromptLocalized("TestService.put.your.answer"))
                .thenReturn("Some answer");

        TestResult result = testService.executeTestFor(student);
        assertEquals(expectedNumberOfCorrectAnswers, result.getRightAnswersCount());
    }

    private List<Question> getQuestions() {
        String questionOne = "Test question 1";
        Answer questionOneAnswerOne = new Answer("Answer 1-1 wrong", false);
        Answer questionOneAnswerTwo = new Answer("Answer 1-2 wrong", false);
        Answer questionOneAnswerThree = new Answer("Answer 1-3 correct", true);
        Question q1 = new Question(questionOne, List.of(questionOneAnswerOne,
                questionOneAnswerTwo,
                questionOneAnswerThree));

        String questionTwo = "Test question 2?";
        Answer questionTwoAnswerOne = new Answer("Answer 1-1 wrong", true);
        Answer questionTwoAnswerTwo = new Answer("Answer 1-2 wrong", false);
        Question q2 = new Question(questionTwo, List.of(questionTwoAnswerOne, questionTwoAnswerTwo));

        return List.of(q1, q2);
    }

    private List<Question> getQuestionWithNoAnswers() {
        String question = "Test question with no answers?";
        Question q = new Question(question, new ArrayList<>());
        return List.of(q);
    }

}