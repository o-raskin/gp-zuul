package ru.olegraskin.sugateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.olegraskin.sugateway.model.Position;
import ru.olegraskin.sugateway.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserDto {

    private Long id;

    private String name;

    private String imageUrl;

    private String position;

    private int gradeProgress;

    public SimpleUserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.imageUrl = user.getImageUrl();

        Position position = user.getPosition();
        if (position != null) {
            this.position = position.getName();
        }
    }
}
