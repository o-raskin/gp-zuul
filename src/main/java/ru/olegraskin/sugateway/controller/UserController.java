package ru.olegraskin.sugateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final ObjectMapper objectMapper;

    //TODO: replace user role for 'HR' and higher in future
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Set<UserDto> getAllUsers(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getAllUsers().stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toSet());
    }

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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public UserDto updateUser(@PathVariable("id") Long id, @RequestBody UserDto dto) {
        dto.setId(id);
        User user = userMapper.dtoToEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.entityToDto(updatedUser);
    }

    @PatchMapping("/{id}")
    public UserDto updateUserWithPatch(@PathVariable Long id, @RequestBody JsonPatch patch)
            throws JsonPatchException, JsonProcessingException {
        User user = userService.getUserById(id);
        JsonNode patchedNode = patch.apply(objectMapper.convertValue(user, JsonNode.class));
        User patchedUser = objectMapper.treeToValue(patchedNode, User.class);

        if (!user.getPosition().equals(patchedUser.getPosition())) {
            // do request to skills app and set grade to 'null'
        }

        User updatedUser = userService.update(patchedUser);
        return userMapper.entityToDto(updatedUser);
    }
}
