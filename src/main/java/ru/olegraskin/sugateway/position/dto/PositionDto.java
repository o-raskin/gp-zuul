package ru.olegraskin.sugateway.position.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.olegraskin.sugateway.position.model.Position;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {

    private Long id;
    private String name;

    public PositionDto(Position position) {
        this.id = position.getId();
        this.name = position.getName();
    }
}
