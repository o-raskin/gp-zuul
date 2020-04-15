package ru.olegraskin.sugateway.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.olegraskin.sugateway.exception.BadRequestException;
import ru.olegraskin.sugateway.exception.ResourceForbiddenException;
import ru.olegraskin.sugateway.exception.ResourceNotFoundException;
import ru.olegraskin.sugateway.model.Profile;
import ru.olegraskin.sugateway.model.User;
import ru.olegraskin.sugateway.repository.ProfileRepository;
import ru.olegraskin.sugateway.service.ProfileService;
import ru.olegraskin.sugateway.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserService userService;

    public Profile getProfileByUserId(@NonNull Long userId) {

        Optional<Profile> optional = profileRepository.findProfileByUserId(userId);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Profile", "usedId", userId);
        }
        return optional.get();
    }

    public Profile getProfileByUserId(@NonNull Long userId, User user) {

        Optional<Profile> optional = profileRepository.findProfileByUserId(userId);
        if (optional.isPresent()) {
            Profile requestedProfile = optional.get();
            if ((!requestedProfile.isVisibility() && requestedProfile.getWhitelist().contains(user)) ||
                    requestedProfile.isVisibility() ||
                    requestedProfile.getUser().equals(user)) {
                return requestedProfile;
            } else {
                throw new ResourceForbiddenException("Profile");
            }
        } else {
            return this.create(userId);
        }
    }

    @Transactional
    @Override
    public Profile create(Long userId) {
        User user = userService.getUserById(userId);

        Profile userProfile = new Profile();
        if (!user.isActive()) {
            throw new BadRequestException("Request profile of inactive user account");
        }

        userProfile.setUser(user);

        Set<User> defaultProfileWhiteList = new HashSet<>();
        User mentor = user.getMentor();
        if (mentor != null) {
            defaultProfileWhiteList.add(user.getMentor());
        }
        //TODO: ADD HR LOGIC OR MANAGER
        userProfile.setWhitelist(defaultProfileWhiteList);

        return profileRepository.save(userProfile);
    }

    @Transactional
    @Override
    public Profile update(Profile profile) {

        Optional<Profile> optional = profileRepository.findProfileByUserId(profile.getUser().getId());
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Profile", "id", profile.getId());
        }

        Profile stored = optional.get();
        stored.setWhitelist(profile.getWhitelist());
        stored.setStatus(profile.getStatus());
        stored.setVisibility(profile.isVisibility());
        return profileRepository.save(stored);
    }

    @Override
    public Set<Profile> getAllProfiles() {
        return new HashSet<>(profileRepository.findAll());
    }


}
