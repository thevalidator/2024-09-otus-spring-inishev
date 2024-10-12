package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final CsvQuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = csvQuestionDao.findAll();
        questions.forEach(this::printQuestionWithAnswers);
    }

    private void printQuestionWithAnswers(Question question) {
        ioService.printLine(question.text());
        question.answers().forEach(a -> ioService.printFormattedLine("\t- %s", a.text()));
    }
}
