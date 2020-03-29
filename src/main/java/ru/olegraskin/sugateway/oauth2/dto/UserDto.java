package ru.olegraskin.sugateway.oauth2.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String imageUrl;

    private String role;

    private String position;

    private Long mentorId;

    private Set<SimpleUserDto> followings;

    private Set<SimpleUserDto> followers;

    private LocalDate lastPromotionDate;

    private LocalDate futurePromotionDate;

    private LocalDate inCompanySince;
}
