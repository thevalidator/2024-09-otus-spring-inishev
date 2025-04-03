package ru.thevalidator.timeattackracing.exception;

public class ConstraintViolationErrorException extends RuntimeException {

    public ConstraintViolationErrorException(String message) {
        super(message);
    }

}
