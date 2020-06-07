package ru.olegraskin.sugateway.client.skills;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.olegraskin.sugateway.client.skills.fallback.SkillsClientFallback;

@FeignClient(value = "skills", fallback = SkillsClientFallback.class)
public interface SkillsClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}",
            produces =  MediaType.APPLICATION_JSON_VALUE
    )
    SkillsUserDto getSkillsUser(@PathVariable("id") Long id);
}
