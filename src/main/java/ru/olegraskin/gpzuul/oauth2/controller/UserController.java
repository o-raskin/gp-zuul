package ru.olegraskin.gpzuul.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.olegraskin.gpzuul.oauth2.exception.ResourceNotFoundException;
import ru.olegraskin.gpzuul.oauth2.model.User;
import ru.olegraskin.gpzuul.oauth2.repository.UserRepository;
import ru.olegraskin.gpzuul.oauth2.security.CurrentUser;
import ru.olegraskin.gpzuul.oauth2.security.UserPrincipal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/current")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
