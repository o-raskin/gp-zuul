package ru.olegraskin.sugateway.profile.dto;

import lombok.Data;
import ru.olegraskin.sugateway.user.dto.SimpleUserDto;

import java.util.Set;

@Data
public class ProfileDto {

    private long id;

    private SimpleUserDto user;

    private boolean visibility;

    private String status;

    private Set<SimpleUserDto> whitelist;
}
