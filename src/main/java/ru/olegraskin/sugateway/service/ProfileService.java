package ru.olegraskin.sugateway.service;

import ru.olegraskin.sugateway.model.Profile;
import ru.olegraskin.sugateway.model.User;

import java.util.Set;

public interface ProfileService {

    Profile getProfileByUserId(Long usedId);

    Profile getProfileByUserId(Long usedId, User user);

    Profile create(Long userId);

    Profile update(Profile userId);

    Set<Profile> getAllProfiles();
}
