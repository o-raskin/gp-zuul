package ru.olegraskin.sugateway.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.olegraskin.sugateway.exception.ResourceNotFoundException;
import ru.olegraskin.sugateway.model.User;
import ru.olegraskin.sugateway.repository.UserRepository;
import ru.olegraskin.sugateway.service.UserService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(@NonNull Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User getUserByEmail(@NonNull String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public Set<User> getSubordinates(@NonNull Long id) {
        return userRepository.findUsersByMentorId(id);
    }

    @Transactional
    @Override
    public User addFollowerToUser(@NonNull Long userId, @NonNull Long followerId) {
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
    public User removeFollowerToUser(@NonNull Long userId, @NonNull Long followerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found with id: " + followerId));
        user.removeFollower(follower);
        follower.removeFollowing(user);
        return userRepository.save(user);
    }

}
