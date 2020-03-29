package ru.olegraskin.sugateway.oauth2.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.olegraskin.sugateway.oauth2.dto.ProfileDto;
import ru.olegraskin.sugateway.oauth2.dto.UserDto;
import ru.olegraskin.sugateway.oauth2.mapper.ProfileMapper;
import ru.olegraskin.sugateway.oauth2.model.Profile;
import ru.olegraskin.sugateway.oauth2.model.User;
import ru.olegraskin.sugateway.oauth2.security.CurrentUser;
import ru.olegraskin.sugateway.oauth2.security.UserPrincipal;
import ru.olegraskin.sugateway.oauth2.service.ProfileService;
import ru.olegraskin.sugateway.oauth2.service.UserService;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileMapper profileMapper;
    private final ProfileService profileService;
    private final UserService userService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ProfileDto getByUserId(@PathVariable("userId") Long userId,
                                  @CurrentUser UserPrincipal userPrincipal) {
        //  profile id == user id
        User user = userService.getUserById(userPrincipal.getId());
        Profile profile = profileService.getProfileByUserId(userId, user);
        return profileMapper.entityToDto(profile);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ProfileDto updateProfile(@PathVariable("id") @NonNull Long id, @RequestBody ProfileDto dto) {
        //  profile id == user id
        dto.setId(id);
        Profile profile = profileMapper.dtoToEntity(dto);
        Profile updated = profileService.update(profile);
        return profileMapper.entityToDto(updated);
    }
}
