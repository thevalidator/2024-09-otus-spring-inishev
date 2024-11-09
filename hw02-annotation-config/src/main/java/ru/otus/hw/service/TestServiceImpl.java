package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

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
            int questionNumber = i + 1;
            printQuestion(questionNumber, question.text());
            boolean isRightAnswer = handleUserAnswer(question.answers());
            testResult.applyAnswer(question, isRightAnswer);
        }
        return testResult;
    }

    private boolean handleUserAnswer(List<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            var answer = answers.get(i);
            ioService.printFormattedLine("\t[%d] %s", i + 1, answer.text());
        }

        boolean isRightAnswer;
        if (answers.isEmpty()) {
            ioService.readStringWithPrompt("Put your answer:");
            isRightAnswer = true;
        } else {
            int input = ioService.readIntForRangeWithPrompt(
                    1,
                    answers.size(),
                    "Choose correct option:",
                    "No such answer, please try again"
            );
            isRightAnswer = answers.get(input - 1).isCorrect();
        }
        return isRightAnswer;
    }

    private void printQuestion(int questionNumber, String question) {
        ioService.printFormattedLine("%nQuestion %d: %s", questionNumber, question);
    }

}
