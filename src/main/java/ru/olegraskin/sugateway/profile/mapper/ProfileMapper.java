package ru.olegraskin.sugateway.profile.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.olegraskin.sugateway.profile.dto.ProfileDto;
import ru.olegraskin.sugateway.user.dto.SimpleUserDto;
import ru.olegraskin.sugateway.profile.model.Profile;
import ru.olegraskin.sugateway.user.model.User;
import ru.olegraskin.sugateway.user.service.UserService;

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
                .map(SimpleUserDto::new)
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
