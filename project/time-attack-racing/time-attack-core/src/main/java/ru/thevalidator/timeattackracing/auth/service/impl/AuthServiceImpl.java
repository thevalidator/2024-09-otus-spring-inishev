package ru.thevalidator.timeattackracing.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thevalidator.timeattackracing.auth.JwtUtil;
import ru.thevalidator.timeattackracing.auth.config.AuthUserDetails;
import ru.thevalidator.timeattackracing.auth.dto.JwtSet;
import ru.thevalidator.timeattackracing.auth.entity.RefreshToken;
import ru.thevalidator.timeattackracing.auth.repository.RefreshTokenRepository;
import ru.thevalidator.timeattackracing.auth.service.AuthService;
import ru.thevalidator.timeattackracing.dto.RefreshTokenRequest;
import ru.thevalidator.timeattackracing.dto.SignInRequest;
import ru.thevalidator.timeattackracing.entity.UserEntity;
import ru.thevalidator.timeattackracing.exception.InvalidRefreshTokenException;
import ru.thevalidator.timeattackracing.exception.UnsupportedUserDetailsType;

import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationProvider authenticationProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthenticationProvider authenticationProvider,
                           RefreshTokenRepository refreshTokenRepository,
                           JwtUtil jwtUtil) {
        this.authenticationProvider = authenticationProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void signOutUser(RefreshTokenRequest rq) {
        refreshTokenRepository.deleteByRefreshToken(rq.getRefreshToken());
    }

    @Override
    public JwtSet refreshTokens(RefreshTokenRequest rq) {
        Optional<RefreshToken> foundToken = refreshTokenRepository.findByRefreshToken(rq.getRefreshToken());
        if (foundToken.isEmpty()) {
            throw new InvalidRefreshTokenException("Refresh token not found");
        }
        RefreshToken refreshToken = foundToken.get();
        throwIfTokenIsNotValid(refreshToken.getRefreshToken());
        UserEntity user = refreshToken.getUser();
        refreshTokenRepository.deleteByRefreshToken(rq.getRefreshToken());
        JwtSet jwtSet = createJwtSetFromUUID(user.getId());
        saveRefreshToken(user, jwtSet.getRefreshToken());

        return jwtSet;
    }

    private void throwIfTokenIsNotValid(String refreshToken) {
        try {
            jwtUtil.validateToken(refreshToken);
        } catch (ParseException e) {
            throw new InvalidRefreshTokenException(e.getMessage(), e);
        }
    }

    @Override
    public JwtSet signInUser(SignInRequest rq) {
        Authentication auth = authenticateUser(rq);
        UserEntity user = ((AuthUserDetails) auth.getPrincipal()).getUser();
        refreshTokenRepository.deleteByUserId(user.getId());
        JwtSet jwtSet = createJwtSetFromAuthentication(auth);
        saveRefreshToken(user, jwtSet.getRefreshToken());

        return jwtSet;
    }

    private Authentication authenticateUser(SignInRequest rq) {
        var authToken = new UsernamePasswordAuthenticationToken(rq.getEmail(), rq.getPassword());
        Authentication auth = authenticationProvider.authenticate(authToken);
        log.debug("Authenticated user: {}, is authenticated: {}", auth.getName(), auth.isAuthenticated());

        if (!(auth.getPrincipal() instanceof AuthUserDetails)) {
            log.error("Authenticated user: {}, is authenticated: {}", auth.getName(), auth.isAuthenticated());
            throw new UnsupportedUserDetailsType("Wrong authentication type: "
                    + auth.getPrincipal().getClass().getName());
        }

        return auth;
    }

    private JwtSet createJwtSetFromAuthentication(Authentication auth) {
        String at = jwtUtil.generateAccessJwtFromAuthentication(auth);
        String rt = jwtUtil.generateRefreshJwtFromAuthentication(auth);
        return new JwtSet(at, rt);
    }

    private JwtSet createJwtSetFromUUID(UUID id) {
        String at = jwtUtil.generateAccessJwtFromUUID(id);
        String rt = jwtUtil.generateRefreshJwtFromUUID(id);
        return new JwtSet(at, rt);
    }

    private void saveRefreshToken(UserEntity user, String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(token);
        refreshTokenRepository.save(refreshToken);
    }

}
