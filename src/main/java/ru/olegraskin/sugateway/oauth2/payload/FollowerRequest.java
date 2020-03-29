package ru.olegraskin.sugateway.oauth2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FollowerRequest {

    @NotNull
    private Long followerId;
}
