package ru.olegraskin.sugateway.oauth2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import ru.olegraskin.sugateway.oauth2.exception.ResourceNotFoundException;
import ru.olegraskin.sugateway.oauth2.model.AuthProvider;
import ru.olegraskin.sugateway.oauth2.model.User;
import ru.olegraskin.sugateway.oauth2.repository.UserRepository;
import ru.olegraskin.sugateway.oauth2.security.oauth2.user.OAuth2UserInfo;
import ru.olegraskin.sugateway.oauth2.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
}
