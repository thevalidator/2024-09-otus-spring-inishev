package ru.thevalidator.timeattackracing.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
public class KeyProperties {

    private RSAPublicKey publicKeyLocation;

    private RSAPrivateKey privateKeyLocation;

    public RSAPublicKey getPublicKeyLocation() {
        return publicKeyLocation;
    }

    public void setPublicKeyLocation(RSAPublicKey publicKeyLocation) {
        this.publicKeyLocation = publicKeyLocation;
    }

    public RSAPrivateKey getPrivateKeyLocation() {
        return privateKeyLocation;
    }

    public void setPrivateKeyLocation(RSAPrivateKey privateKeyLocation) {
        this.privateKeyLocation = privateKeyLocation;
    }

}
