package ru.olegraskin.sugateway.oauth2.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.olegraskin.sugateway.oauth2.exception.BadRequestException;
import ru.olegraskin.sugateway.oauth2.exception.ResourceNotFoundException;
import ru.olegraskin.sugateway.oauth2.model.Profile;
import ru.olegraskin.sugateway.oauth2.model.User;
import ru.olegraskin.sugateway.oauth2.repository.ProfileRepository;
import ru.olegraskin.sugateway.oauth2.service.ProfileService;
import ru.olegraskin.sugateway.oauth2.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserService userService;

    /**
     * Get profile or create new and save it
     *
     * @param userId
     * @return profile
     */
    public Profile getProfileByUserId(@NonNull Long userId) {
        Optional<Profile> optional = profileRepository.findProfileByUserId(userId);
        return optional.orElseGet(() -> this.create(userId));
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
        userProfile.setId(userId);

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
        stored.setFollowers(profile.getFollowers());
        stored.setWhitelist(profile.getWhitelist());
        stored.setStatus(profile.getStatus());
        stored.setVisibility(profile.isVisibility());
        profileRepository.save(stored);

        return stored;
    }


}
