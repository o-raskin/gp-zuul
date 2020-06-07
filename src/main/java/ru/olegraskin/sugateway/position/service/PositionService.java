package ru.olegraskin.sugateway.position.service;

import ru.olegraskin.sugateway.position.model.Position;

import java.util.Set;

public interface PositionService {

    Position getByName(String positionName);

    Set<Position> getAll();
}
