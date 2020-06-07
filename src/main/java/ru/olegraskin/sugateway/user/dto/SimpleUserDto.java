package ru.olegraskin.sugateway.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.olegraskin.sugateway.position.dto.PositionDto;
import ru.olegraskin.sugateway.position.model.Position;
import ru.olegraskin.sugateway.user.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserDto {

    private Long id;

    private String name;

    private String imageUrl;

    private PositionDto position;

    public SimpleUserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.imageUrl = user.getImageUrl();

        Position position = user.getPosition();
        if (position != null) {
            this.position = new PositionDto(position.getId(), position.getName());
        }
    }
}
