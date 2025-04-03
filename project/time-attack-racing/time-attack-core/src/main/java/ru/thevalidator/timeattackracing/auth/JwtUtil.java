package ru.thevalidator.timeattackracing.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import ru.thevalidator.timeattackracing.auth.config.AuthUserDetails;
import ru.thevalidator.timeattackracing.auth.config.JwtLifetimeProperties;
import ru.thevalidator.timeattackracing.exception.InvalidRefreshTokenException;
import ru.thevalidator.timeattackracing.service.UserService;

import java.text.ParseException;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private static final String ISSUER = "time-attack-racing";

    private static final String CLAIM_ISS = "iss";

    private static final String CLAIM_SCP = "scp";

    private final JwtEncoder encoder;

    private final JwtDecoder decoder;

    private final JwtLifetimeProperties jwtLifetimeProperties;

    private final UserService userService;

    public JwtUtil(JwtEncoder encoder,
                   JwtDecoder decoder,
                   JwtLifetimeProperties jwtLifetimeProperties,
                   UserService userService) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.jwtLifetimeProperties = jwtLifetimeProperties;
        this.userService = userService;
    }

    public void validateToken(String token) throws ParseException {
        Jwt parsedJwt = decoder.decode(token);
        if (!ISSUER.equals(parsedJwt.getClaimAsString(CLAIM_ISS))) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
    }

    public String generateAccessJwtFromAuthentication(Authentication authentication) {
        String subject = getSubjectFromAuthentication(authentication);
        return generateAccessJwt(subject, authentication.getAuthorities());
    }

    public String generateRefreshJwtFromAuthentication(Authentication authentication) {
        String subject = getSubjectFromAuthentication(authentication);
        return generateRefreshJwt(subject);
    }

    private String getSubjectFromAuthentication(Authentication authentication) {
        AuthUserDetails ud = (AuthUserDetails) authentication.getPrincipal();
        return ud.getUser().getId().toString();
    }

    public String generateAccessJwt(String sub, Collection<? extends GrantedAuthority> grantedAuthorities) {
        Instant now = Instant.now();
        long expiryLifetimeSeconds = TimeUnit.MINUTES.toSeconds(jwtLifetimeProperties.getMinutes());
        Collection<String> authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiryLifetimeSeconds))
                .subject(sub)
                .claim(CLAIM_SCP, authorities)
                .build();
        String token = encoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
        log.debug("Access JWT has been successfully generated");
        return token;
    }

    public String generateRefreshJwt(String sub) {
        Instant now = Instant.now();
        long expiryLifetimeSeconds = TimeUnit.HOURS.toSeconds(jwtLifetimeProperties.getHours());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiryLifetimeSeconds))
                .subject(sub)
                .build();
        String token = encoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
        log.debug("Refresh JWT has been successfully generated");
        return token;
    }

    public String generateAccessJwtFromUUID(UUID id) {
        var user = userService.getUserById(id);
        List<SimpleGrantedAuthority> scopes = user.getRole()
                .getScopes()
                .stream()
                .map(scope -> new SimpleGrantedAuthority(scope.getCode())).toList();
        return generateAccessJwt(id.toString(), scopes);
    }

    public String generateRefreshJwtFromUUID(UUID id) {
        return generateRefreshJwt(id.toString());
    }

}
