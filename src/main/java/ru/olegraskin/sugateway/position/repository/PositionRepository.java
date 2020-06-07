package ru.olegraskin.sugateway.position.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.olegraskin.sugateway.position.model.Position;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByName(String positionName);
}
