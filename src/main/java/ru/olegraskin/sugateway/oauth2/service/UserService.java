package ru.olegraskin.sugateway.oauth2.service;

import ru.olegraskin.sugateway.oauth2.model.User;

public interface UserService {

    User getUserById(Long id);
}
