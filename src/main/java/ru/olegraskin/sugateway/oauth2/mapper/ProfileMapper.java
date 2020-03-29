package ru.olegraskin.sugateway.oauth2.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.olegraskin.sugateway.oauth2.dto.ProfileDto;
import ru.olegraskin.sugateway.oauth2.dto.SimpleUserDto;
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

        Set<SimpleUserDto> whiteList = profile.getWhitelist().stream()
                .map(u -> new SimpleUserDto(u.getId(), u.getName(), u.getImageUrl()))
                .collect(Collectors.toSet());
        dto.setWhitelist(whiteList);

        return dto;
    }

    public Profile dtoToEntity(ProfileDto dto) {
        Profile entity = modelMapper.map(dto, Profile.class);

        Set<User> whiteList = dto.getWhitelist().stream()
                .map(su -> userService.getUserById(su.getId()))
                .collect(Collectors.toSet());
        entity.setWhitelist(whiteList);

        return entity;
    }
}
