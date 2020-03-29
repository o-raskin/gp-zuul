package ru.olegraskin.sugateway.oauth2.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProfileDto {

    private long id;

    private long userId;

    private boolean visibility;

    private String status;

    private Set<SimpleUserDto> whitelist;
}
