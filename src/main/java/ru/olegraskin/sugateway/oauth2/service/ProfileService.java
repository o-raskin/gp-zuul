package ru.olegraskin.sugateway.oauth2.service;

import ru.olegraskin.sugateway.oauth2.dto.ProfileDto;
import ru.olegraskin.sugateway.oauth2.model.Profile;
import ru.olegraskin.sugateway.oauth2.model.User;

public interface ProfileService {

//    Profile getProfileByUserId(Long usedId);

    Profile getProfileByUserId(Long usedId, User user);

    Profile create(Long userId);

    Profile update(Profile userId);
}
