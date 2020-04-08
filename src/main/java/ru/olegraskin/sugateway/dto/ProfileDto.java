package ru.olegraskin.sugateway.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProfileDto {

    private long id;

    private SimpleUserDto user;

    private boolean visibility;

    private String status;

    private Set<SimpleUserDto> whitelist;
}
