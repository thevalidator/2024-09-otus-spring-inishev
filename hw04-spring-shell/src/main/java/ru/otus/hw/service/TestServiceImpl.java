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

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

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
            ioService.readStringWithPromptLocalized("TestService.put.your.answer");
            isRightAnswer = true;
        } else {
            int input = ioService.readIntForRangeWithPromptLocalized(
                    1,
                    answers.size(),
                    "TestService.choose.correct.option",
                    "TestService.no.such.answer"
            );
            isRightAnswer = answers.get(input - 1).isCorrect();
        }
        return isRightAnswer;
    }

    private void printQuestion(int questionNumber, String question) {
        ioService.printLine("");
        ioService.printFormattedLineLocalized("TestService.question", questionNumber, question);
    }

}
