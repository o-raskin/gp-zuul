package ru.olegraskin.sugateway.user.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.olegraskin.sugateway.security.model.AuthProvider;
import ru.olegraskin.sugateway.user.model.User;
import ru.olegraskin.sugateway.user.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InitialTask {

    public static final String SYSTEM_SKILLUP_COM_EMAIL = "system@skillup.com";
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        initSystemUser();
    }

    @Transactional
    public void initSystemUser() {
        Optional<User> optionalSystemUser = userRepository.findByEmail(SYSTEM_SKILLUP_COM_EMAIL);
        if (!optionalSystemUser.isPresent()) {
            User user = new User();
            user.setId(0L);
            user.setName("SYSTEM");
            user.setEmail(SYSTEM_SKILLUP_COM_EMAIL);
            user.setProvider(AuthProvider.local);
            userRepository.save(user);
        }
    }
}
