package ru.olegraskin.sugateway.client.fallback;

import org.springframework.stereotype.Component;
import ru.olegraskin.sugateway.client.SkillsClient;
import ru.olegraskin.sugateway.dto.skills.GradeDto;
import ru.olegraskin.sugateway.dto.skills.SkillsUserDto;

@Component
public class SkillsClientFallback implements SkillsClient {

    @Override
    public SkillsUserDto getSkillsUser(Long id) {
        return new SkillsUserDto(id, new GradeDto());
    }
}
