package ru.olegraskin.sugateway.controller;

import com.google.common.collect.Sets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.olegraskin.sugateway.security.Role;

import java.util.Arrays;
import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @GetMapping
    public Set<Role> getAll() {
        return Sets.newLinkedHashSet(Arrays.asList(Role.values()));
    }
}
