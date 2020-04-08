package ru.olegraskin.sugateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.olegraskin.sugateway.client.fallback.SkillsClientFallback;
import ru.olegraskin.sugateway.dto.skills.SkillsUserDto;

@FeignClient(value = "skills", fallback = SkillsClientFallback.class)
public interface SkillsClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}",
            produces =  MediaType.APPLICATION_JSON_VALUE
    )
    SkillsUserDto getSkillsUser(@PathVariable("id") Long id);
}
