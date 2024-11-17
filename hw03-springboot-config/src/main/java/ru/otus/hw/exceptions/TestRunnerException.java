package ru.otus.hw.exceptions;

public class TestRunnerException extends RuntimeException {

    public TestRunnerException(String message, Throwable ex) {
        super(message, ex);
    }

}
