package ru.thevalidator.timeattackracing.auth.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.thevalidator.timeattackracing.entity.UserEntity;

import java.util.Collection;

public class AuthUserDetails implements UserDetails {

    private final UserEntity user;

    public AuthUserDetails(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().getScopes()
                .stream()
                .map(scope -> new SimpleGrantedAuthority(scope.getCode()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /*@Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();   //@TODO: implement
    }*/

    public UserEntity getUser() {
        return user;
    }

}
