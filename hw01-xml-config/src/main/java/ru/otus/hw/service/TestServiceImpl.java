package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final CsvQuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = csvQuestionDao.findAll();
        AtomicInteger counter = new AtomicInteger();
        questions.forEach(q -> printQuestionWithAnswers(counter.incrementAndGet(), q));
    }

    private void printQuestionWithAnswers(int number, Question question) {
        ioService.printFormattedLine("%2d) %s", number, question.text());
        question.answers().forEach(a -> ioService.printFormattedLine("\t- %s", a.text()));
    }
}
