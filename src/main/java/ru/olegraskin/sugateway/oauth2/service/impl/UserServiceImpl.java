package ru.olegraskin.sugateway.oauth2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.olegraskin.sugateway.oauth2.exception.ResourceNotFoundException;
import ru.olegraskin.sugateway.oauth2.model.AuthProvider;
import ru.olegraskin.sugateway.oauth2.model.User;
import ru.olegraskin.sugateway.oauth2.repository.UserRepository;
import ru.olegraskin.sugateway.oauth2.security.oauth2.user.OAuth2UserInfo;
import ru.olegraskin.sugateway.oauth2.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Transactional
    @Override
    public User addFollowerToUser(Long userId, Long followerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + followerId));
        user.addFollower(follower);
        follower.addFollowing(user);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User removeFollowerToUser(Long userId, Long followerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found with id: " + followerId));
        user.removeFollower(follower);
        follower.removeFollowing(user);
        return userRepository.save(user);
    }

}
