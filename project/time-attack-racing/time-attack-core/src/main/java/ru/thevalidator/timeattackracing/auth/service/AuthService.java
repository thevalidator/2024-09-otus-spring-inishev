package ru.thevalidator.timeattackracing.auth.service;

import ru.thevalidator.timeattackracing.auth.dto.JwtSet;
import ru.thevalidator.timeattackracing.dto.RefreshTokenRequest;
import ru.thevalidator.timeattackracing.dto.SignInRequest;

public interface AuthService {

    JwtSet signInUser(SignInRequest rq);

    void signOutUser(RefreshTokenRequest rq);

    JwtSet refreshTokens(RefreshTokenRequest rq);

}
