package ru.olegraskin.sugateway.user.dto;

import lombok.Data;
import ru.olegraskin.sugateway.position.dto.PositionDto;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String imageUrl;

    private String role;

    private PositionDto position;

    private SimpleUserDto mentor;

    private Set<SimpleUserDto> followings;

    private Set<SimpleUserDto> followers;

    private LocalDate lastPromotionDate;

    private LocalDate futurePromotionDate;

    private LocalDate inCompanySince;

    private boolean active;
}
