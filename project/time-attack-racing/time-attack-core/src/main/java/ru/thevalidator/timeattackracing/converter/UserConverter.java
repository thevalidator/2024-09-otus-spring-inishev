package ru.thevalidator.timeattackracing.converter;

import org.springframework.stereotype.Component;
import ru.thevalidator.timeattackracing.dto.UserDto;
import ru.thevalidator.timeattackracing.entity.UserEntity;

@Component
public class UserConverter {

    public UserDto toUserDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setBirthDate(user.getBirthDate());
        return userDto;
    }

}
