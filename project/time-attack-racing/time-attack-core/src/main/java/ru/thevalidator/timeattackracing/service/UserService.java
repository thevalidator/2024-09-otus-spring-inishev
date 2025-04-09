package ru.thevalidator.timeattackracing.service;

import ru.thevalidator.timeattackracing.dto.UserRegistrationRequest;
import ru.thevalidator.timeattackracing.entity.RoleName;
import ru.thevalidator.timeattackracing.entity.UserEntity;

import java.util.UUID;

public interface UserService {

    void createUser(final UserRegistrationRequest userRegistrationRequest);

    UserEntity getUserById(final UUID userId);

    UserEntity getUserByEmail(final String email);

    void applyRole(UUID userId, RoleName role);

}
