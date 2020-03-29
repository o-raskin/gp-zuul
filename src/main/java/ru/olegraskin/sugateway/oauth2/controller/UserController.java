package ru.olegraskin.sugateway.oauth2.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.olegraskin.sugateway.oauth2.dto.SimpleUserDto;
import ru.olegraskin.sugateway.oauth2.dto.UserDto;
import ru.olegraskin.sugateway.oauth2.mapper.UserMapper;
import ru.olegraskin.sugateway.oauth2.model.User;
import ru.olegraskin.sugateway.oauth2.payload.FollowerRequest;
import ru.olegraskin.sugateway.oauth2.security.CurrentUser;
import ru.olegraskin.sugateway.oauth2.security.UserPrincipal;
import ru.olegraskin.sugateway.oauth2.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/current")
    @PreAuthorize("hasRole('USER')")
    public UserDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUserById(userPrincipal.getId());
        return userMapper.entityToDto(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public UserDto getUserById(@PathVariable("id") @NonNull Long id) {
        User user = userService.getUserById(id);
        return userMapper.entityToDto(user);
    }

    @PostMapping("/{id}/follow")
    @PreAuthorize("hasRole('USER')")
    public SimpleUserDto followUser(@PathVariable("id") Long userId, @RequestBody FollowerRequest payload) {
        User user = userService.addFollowerToUser(userId, payload.getFollowerId());
        return new SimpleUserDto(user.getId(), user.getName(), user.getImageUrl());
    }

    @PostMapping("/{id}/unfollow")
    @PreAuthorize("hasRole('USER')")
    public SimpleUserDto unFollowUser(@PathVariable("id") Long userId, @RequestBody FollowerRequest payload) {
        User user = userService.removeFollowerToUser(userId, payload.getFollowerId());
        return new SimpleUserDto(user.getId(), user.getName(), user.getImageUrl());
    }

}
