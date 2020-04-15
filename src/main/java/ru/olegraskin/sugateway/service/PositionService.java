package ru.olegraskin.sugateway.service;

import ru.olegraskin.sugateway.model.Position;

import java.util.Set;

public interface PositionService {

    Position getByName(String positionName);

    Set<Position> getAll();
}
