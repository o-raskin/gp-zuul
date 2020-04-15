package ru.olegraskin.sugateway.service;

import ru.olegraskin.sugateway.dto.UserDto;
import ru.olegraskin.sugateway.model.User;

import java.util.Set;

public interface UserService {

    User getUserById(Long id);

    User addFollowerToUser(Long userId, Long followerId);

    User removeFollowerToUser(Long userId, Long followerId);

    User getUserByEmail(String email);

    Set<User> getSubordinates(Long id);

    Set<User> getAllUsers();

    User update(User user);
}
