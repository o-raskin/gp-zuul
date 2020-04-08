package ru.olegraskin.sugateway.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FollowerRequest {

    @NotNull
    private Long followerId;
}
