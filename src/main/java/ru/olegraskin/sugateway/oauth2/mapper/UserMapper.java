package ru.olegraskin.sugateway.oauth2.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.olegraskin.sugateway.oauth2.dto.ProfileDto;
import ru.olegraskin.sugateway.oauth2.dto.UserDto;
import ru.olegraskin.sugateway.oauth2.model.Profile;
import ru.olegraskin.sugateway.oauth2.model.User;
import ru.olegraskin.sugateway.oauth2.service.ProfileService;
import ru.olegraskin.sugateway.oauth2.service.UserService;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProfileService profileService;

    public UserDto entityToDto(User entity) {
        UserDto dto = modelMapper.map(entity, UserDto.class);

        if (entity.getMentor() != null) {
            dto.setMentorId(entity.getMentor().getId());
        }

        Set<Long> followingsIds = entity.getFollowing().stream()
                .map(Profile::getId)
                .collect(Collectors.toSet());
        dto.setFollowingProfilesIds(followingsIds);

        return dto;
    }

    public User dtoToEntity(UserDto dto) {
        User entity = modelMapper.map(dto, User.class);

        if (dto.getMentorId() != null) {
            User user = userService.getUserById(dto.getId());
            entity.setMentor(user);
        }

        Set<Profile> followings = dto.getFollowingProfilesIds().stream()
                .filter(Objects::nonNull)
                .map(profileService::getProfileByUserId)
                .collect(Collectors.toSet());
        entity.setFollowing(followings);

        return entity;
    }
}
