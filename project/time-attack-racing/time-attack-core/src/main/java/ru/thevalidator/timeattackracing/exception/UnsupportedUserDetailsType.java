package ru.thevalidator.timeattackracing.exception;

import org.springframework.security.core.AuthenticationException;

public class UnsupportedUserDetailsType extends AuthenticationException {

    public UnsupportedUserDetailsType(String msg) {
        super(msg);
    }

}
