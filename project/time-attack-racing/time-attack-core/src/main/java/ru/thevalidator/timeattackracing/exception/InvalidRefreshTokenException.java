package ru.thevalidator.timeattackracing.exception;

public class InvalidRefreshTokenException extends RuntimeException {


    public InvalidRefreshTokenException(String message, Throwable e) {
        super(message, e);
    }

    public InvalidRefreshTokenException(String message) {
        super(message);
    }

}
