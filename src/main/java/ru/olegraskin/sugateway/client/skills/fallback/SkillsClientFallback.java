package ru.olegraskin.sugateway.client.skills.fallback;

import org.springframework.stereotype.Component;
import ru.olegraskin.sugateway.client.skills.SkillsClient;
import ru.olegraskin.sugateway.client.skills.GradeDto;
import ru.olegraskin.sugateway.client.skills.SkillsUserDto;

@Component
public class SkillsClientFallback implements SkillsClient {

    @Override
    public SkillsUserDto getSkillsUser(Long id) {
        return new SkillsUserDto(id, new GradeDto());
    }
}
