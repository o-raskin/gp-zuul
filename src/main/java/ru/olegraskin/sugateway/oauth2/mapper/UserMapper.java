package ru.olegraskin.sugateway.oauth2.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.olegraskin.sugateway.oauth2.dto.SimpleUserDto;
import ru.olegraskin.sugateway.oauth2.dto.UserDto;
import ru.olegraskin.sugateway.oauth2.model.User;
import ru.olegraskin.sugateway.oauth2.service.UserService;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserDto entityToDto(User entity) {
        UserDto dto = modelMapper.map(entity, UserDto.class);

        if (entity.getMentor() != null) {
            dto.setMentorId(entity.getMentor().getId());
        }

        Set<SimpleUserDto> followings = entity.getFollowing().stream()
                .map(e -> new SimpleUserDto(e.getId(), e.getName(), e.getImageUrl()))
                .collect(Collectors.toSet());
        dto.setFollowings(followings);

        Set<SimpleUserDto> followers = entity.getUserFollowers().stream()
                .map(e -> new SimpleUserDto(e.getId(), e.getName(), e.getImageUrl()))
                .collect(Collectors.toSet());
        dto.setFollowers(followers);

        return dto;
    }

    public User dtoToEntity(UserDto dto) {
        User entity = modelMapper.map(dto, User.class);

        if (dto.getMentorId() != null) {
            User user = userService.getUserById(dto.getId());
            entity.setMentor(user);
        }

        Set<User> followings = dto.getFollowings().stream()
                .filter(Objects::nonNull)
                .map(f -> userService.getUserById(f.getId()))
                .collect(Collectors.toSet());
        entity.setFollowing(followings);

        Set<User> followers = dto.getFollowers().stream()
                .filter(Objects::nonNull)
                .map(f -> userService.getUserById(f.getId()))
                .collect(Collectors.toSet());
        entity.setUserFollowers(followers);

        return entity;
    }
}
