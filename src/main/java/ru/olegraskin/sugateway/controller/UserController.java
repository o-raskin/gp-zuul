package ru.olegraskin.sugateway.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.olegraskin.sugateway.client.SkillsClient;
import ru.olegraskin.sugateway.dto.SimpleUserDto;
import ru.olegraskin.sugateway.dto.UserDto;
import ru.olegraskin.sugateway.dto.skills.GradeDto;
import ru.olegraskin.sugateway.dto.skills.SkillsUserDto;
import ru.olegraskin.sugateway.mapper.UserMapper;
import ru.olegraskin.sugateway.model.User;
import ru.olegraskin.sugateway.payload.FollowerRequest;
import ru.olegraskin.sugateway.security.CurrentUser;
import ru.olegraskin.sugateway.security.UserPrincipal;
import ru.olegraskin.sugateway.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    public static final int DEFAULT_GRADE_PROGRESS = 0;
    private final UserService userService;
    private final UserMapper userMapper;
    private final SkillsClient skillsClient;

    @GetMapping("/current")
    @PreAuthorize("hasRole('USER')")
    public UserDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUserById(userPrincipal.getId());
        return userMapper.entityToDto(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('SYSTEM')")
    public UserDto getUserById(@PathVariable("id") @NonNull Long id) {
        User user = userService.getUserById(id);
        return userMapper.entityToDto(user);
    }

    @PostMapping("/{id}/follow")
    @PreAuthorize("hasRole('USER')")
    public SimpleUserDto followUser(@PathVariable("id") Long userId, @RequestBody FollowerRequest payload) {
        User user = userService.addFollowerToUser(userId, payload.getFollowerId());
        return new SimpleUserDto(user);
    }

    @PostMapping("/{id}/unfollow")
    @PreAuthorize("hasRole('USER')")
    public SimpleUserDto unFollowUser(@PathVariable("id") Long userId, @RequestBody FollowerRequest payload) {
        User user = userService.removeFollowerToUser(userId, payload.getFollowerId());
        return new SimpleUserDto(user);
    }

    @GetMapping("/{id}/subordinates")
    @PreAuthorize("hasRole('USER')")
    public Set<SimpleUserDto> getUserSubordinates(@PathVariable("id") @NonNull Long id) {
        Set<User> subordinates = userService.getSubordinates(id);
        return subordinates.stream()
                .map(SimpleUserDto::new)
                .peek(s -> {
                    SkillsUserDto skillsUser = skillsClient.getSkillsUser(s.getId());
                    GradeDto userGrade = skillsUser.getGrade();
                    if (userGrade != null) {
                        s.setGradeProgress(userGrade.getGradeProgress());
                    } else {
                        s.setGradeProgress(DEFAULT_GRADE_PROGRESS);
                    }
                })
                .collect(Collectors.toSet());
    }

}
