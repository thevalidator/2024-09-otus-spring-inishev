package ru.thevalidator.timeattackracing.controller.v1;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.thevalidator.timeattackracing.auth.dto.JwtSet;
import ru.thevalidator.timeattackracing.auth.service.AuthService;
import ru.thevalidator.timeattackracing.dto.RefreshTokenRequest;
import ru.thevalidator.timeattackracing.dto.SignInRequest;
import ru.thevalidator.timeattackracing.dto.UserRegistrationRequest;
import ru.thevalidator.timeattackracing.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/auth/sign-up")
    public void register(@Valid @RequestBody UserRegistrationRequest rq) {
        userService.createUser(rq);
    }

    @PostMapping("/auth/sign-in")
    public JwtSet login(@Valid @RequestBody SignInRequest rq) {
        return authService.signInUser(rq);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/auth/sign-out")
    public void logout(@Valid @RequestBody RefreshTokenRequest rq) {
        authService.signOutUser(rq);
    }

    @PostMapping("/auth/token/refresh")
    public JwtSet refreshToken(@Valid @RequestBody RefreshTokenRequest rq) {
        return authService.refreshTokens(rq);
    }

}
