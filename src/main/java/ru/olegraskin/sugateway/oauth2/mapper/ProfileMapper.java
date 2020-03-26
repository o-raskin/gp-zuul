package ru.olegraskin.sugateway.oauth2.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.olegraskin.sugateway.oauth2.dto.ProfileDto;
import ru.olegraskin.sugateway.oauth2.model.Profile;
import ru.olegraskin.sugateway.oauth2.model.User;
import ru.olegraskin.sugateway.oauth2.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProfileDto entityToDto(Profile profile) {
        ProfileDto dto = modelMapper.map(profile, ProfileDto.class);

        Set<Long> whiteList = profile.getWhitelist().stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        dto.setWhitelistIds(whiteList);

        Set<Long> followers = profile.getFollowers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        dto.setFollowersIds(followers);

        return dto;
    }

    public Profile dtoToEntity(ProfileDto dto) {
        Profile entity = modelMapper.map(dto, Profile.class);

        Set<User> whiteList = dto.getWhitelistIds().stream()
                .map(userService::getUserById)
                .collect(Collectors.toSet());
        entity.setWhitelist(whiteList);

        Set<User> followers = dto.getFollowersIds().stream()
                .map(userService::getUserById)
                .collect(Collectors.toSet());
        entity.setFollowers(followers);

        return entity;
    }
}
