package ru.olegraskin.sugateway.user.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.olegraskin.sugateway.user.dto.SimpleUserDto;
import ru.olegraskin.sugateway.user.model.User;
import ru.olegraskin.sugateway.user.service.UserService;

@Component
@RequiredArgsConstructor
public class SimpleUserMapper {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public SimpleUserDto entityToDto(User user) {
        return modelMapper.map(user, SimpleUserDto.class);
    }

    public User dtoToEntity(SimpleUserDto simpleUserDto) {
        User user = userService.getUserById(simpleUserDto.getId());
        modelMapper.map(simpleUserDto, user);
        return user;
    }
}
