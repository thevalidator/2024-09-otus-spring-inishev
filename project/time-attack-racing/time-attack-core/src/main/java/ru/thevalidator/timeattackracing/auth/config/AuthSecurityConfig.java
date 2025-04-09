package ru.thevalidator.timeattackracing.auth.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import ru.thevalidator.timeattackracing.entity.UserEntity;
import ru.thevalidator.timeattackracing.repository.UserRepository;

import java.util.Map;

@Configuration
public class AuthSecurityConfig {

    public static final String BCRYPT_ENCODER_ID = "bcrypt";

    private final KeyProperties keyProperties;

    private final UserRepository userRepository;

    public AuthSecurityConfig(KeyProperties keyProperties, UserRepository userRepository) {
        this.keyProperties = keyProperties;
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //This will allow to use new encoders in future
        Map<String, PasswordEncoder> encoders = Map.of(
                BCRYPT_ENCODER_ID, new BCryptPasswordEncoder()
        );
        return new DelegatingPasswordEncoder(BCRYPT_ENCODER_ID, encoders);
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey
                .Builder(keyProperties.getPublicKeyLocation())
                .privateKey(keyProperties.getPrivateKeyLocation())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            UserEntity user = userRepository
                    .findByEmailIgnoreCase(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new AuthUserDetails(user);
        };
    }

    @Bean("daoAuthenticationProvider")
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}
