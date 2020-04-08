package ru.olegraskin.sugateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.olegraskin.sugateway.dto.ProfileDto;
import ru.olegraskin.sugateway.mapper.ProfileMapper;
import ru.olegraskin.sugateway.model.Profile;
import ru.olegraskin.sugateway.model.User;
import ru.olegraskin.sugateway.security.CurrentUser;
import ru.olegraskin.sugateway.security.UserPrincipal;
import ru.olegraskin.sugateway.service.ProfileService;
import ru.olegraskin.sugateway.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Getting all profiles of 'active' users
     * @return Set of profiles
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Set<ProfileDto> getAllProfiles() {
        return profileService.getAllProfiles().stream()
                .map(profileMapper::entityToDto)
                .collect(Collectors.toSet());
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ProfileDto updateProfile(@RequestBody ProfileDto dto) {
        Profile profile = profileMapper.dtoToEntity(dto);
        Profile updated = profileService.update(profile);
        return profileMapper.entityToDto(updated);
    }
}
