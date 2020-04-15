package ru.olegraskin.sugateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.olegraskin.sugateway.model.Position;
import ru.olegraskin.sugateway.model.Profile;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByName(String positionName);
}
