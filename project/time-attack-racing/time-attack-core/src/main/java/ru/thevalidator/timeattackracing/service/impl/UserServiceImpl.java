package ru.thevalidator.timeattackracing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thevalidator.timeattackracing.dto.UserRegistrationRequest;
import ru.thevalidator.timeattackracing.entity.RoleEntity;
import ru.thevalidator.timeattackracing.entity.RoleName;
import ru.thevalidator.timeattackracing.entity.UserEntity;
import ru.thevalidator.timeattackracing.exception.ItemNotFoundException;
import ru.thevalidator.timeattackracing.exception.UserAlreadyExistsException;
import ru.thevalidator.timeattackracing.repository.RoleRepository;
import ru.thevalidator.timeattackracing.repository.UserRepository;
import ru.thevalidator.timeattackracing.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static ru.thevalidator.timeattackracing.entity.RoleName.USER;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserRegistrationRequest dto) {
        checkIfUserExists(dto);
        UserEntity newUser = userRepository.save(convertToUser(dto));
        log.info("User created: [id={}]", newUser.getId());
    }

    private void checkIfUserExists(UserRegistrationRequest dto) {
        Optional<UserEntity> found = userRepository.findByEmailIgnoreCase(dto.getEmail());
        found.ifPresent(u -> {throw new UserAlreadyExistsException();});
    }

    private UserEntity convertToUser(UserRegistrationRequest dto) {
        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setBirthDate(dto.getBirthDate());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        setDefaultRole(user);
        return user;
    }

    private void setDefaultRole(UserEntity user) {
        RoleEntity defaultRole = roleRepository.findByName(USER)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Role %s not found", USER)));
        user.setRole(defaultRole);
    }

    @Override
    public UserEntity getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("User not found [id=%s].", userId)));
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ItemNotFoundException(String.format("User not found [email=%s].", email)));
    }

    @Override
    public void applyRole(UUID userId, RoleName role) {
        UserEntity user = getUserById(userId);
        RoleEntity newRole = roleRepository.findByName(role)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Role %s not found.", role)));
        user.setRole(newRole);
        user = userRepository.save(user);
        log.info("User [id={}] was promoted to {} role", user.getId(), role);
    }

}
