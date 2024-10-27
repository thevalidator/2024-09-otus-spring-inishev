package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        for (int i = 0; i < questions.size(); i++) {
            var question = questions.get(i);
            printQuestionWithAnswers(i + 1, question);
            handleAnswer(question, testResult);
        }
        return testResult;
    }

    private void handleAnswer(Question question, TestResult testResult) {
        if (question.answers().isEmpty()) {
            String answer = ioService.readStringWithPrompt("Put your answer:");
            while (answer.isEmpty()) {
                answer = ioService.readStringWithPrompt("The answer can't be empty, please try again");
            }
            testResult.applyAnswer(question, true);
        } else {
            int maxIndex = question.answers().size() - 1;
            int index = ioService.readIntForRangeWithPrompt(
                    0,
                    maxIndex,
                    "Choose correct option:",
                    "No such answer, please try again"
            );
            var isRightAnswer = question.answers().get(index).isCorrect();
            testResult.applyAnswer(question, isRightAnswer);
        }
    }

    private void printQuestionWithAnswers(int number, Question question) {
        ioService.printFormattedLine("%nQuestion %d: %s", number, question.text());
        for (int i = 0; i < question.answers().size(); i++) {
            var answer = question.answers().get(i);
            ioService.printFormattedLine("\t[%d] %s", i, answer.text());
        }
    }

}
