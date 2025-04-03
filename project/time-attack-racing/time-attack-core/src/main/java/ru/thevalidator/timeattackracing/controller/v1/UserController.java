package ru.thevalidator.timeattackracing.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.thevalidator.timeattackracing.converter.UserConverter;
import ru.thevalidator.timeattackracing.dto.UserDto;
import ru.thevalidator.timeattackracing.entity.RoleName;
import ru.thevalidator.timeattackracing.entity.UserEntity;
import ru.thevalidator.timeattackracing.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    private final UserConverter userConverter;

    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PreAuthorize("hasAuthority('SCOPE_READ_USER_DATA') or #userId.toString() == authentication.name")
    @GetMapping("/users/{user_id}")
    public UserDto getUserById(@PathVariable(name = "user_id") UUID userId) {
        UserEntity user = userService.getUserById(userId);
        return userConverter.toUserDto(user);
    }

    @PreAuthorize("hasAuthority('SCOPE_APPLY_ROLES')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/users/{user_id}/set-role")
    public void updateUser(@PathVariable(name = "user_id") UUID userId, @RequestParam RoleName role) {
        userService.applyRole(userId, role);
    }

}
