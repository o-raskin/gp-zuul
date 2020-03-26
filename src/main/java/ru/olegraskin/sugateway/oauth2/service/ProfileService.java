package ru.olegraskin.sugateway.oauth2.service;

import ru.olegraskin.sugateway.oauth2.dto.ProfileDto;
import ru.olegraskin.sugateway.oauth2.model.Profile;

public interface ProfileService {

    Profile getProfileByUserId(Long usedId);

    Profile create(Long userId);

    Profile update(Profile userId);
}
