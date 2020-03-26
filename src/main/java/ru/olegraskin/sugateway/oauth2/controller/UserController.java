package ru.olegraskin.sugateway.oauth2.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.olegraskin.sugateway.oauth2.dto.UserDto;
import ru.olegraskin.sugateway.oauth2.mapper.UserMapper;
import ru.olegraskin.sugateway.oauth2.model.User;
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

}
