package ru.olegraskin.sugateway.position.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.olegraskin.sugateway.position.model.Position;
import ru.olegraskin.sugateway.position.repository.PositionRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public Position getByName(String positionName) {
        return positionRepository.findByName(positionName)
                .orElse(null);
    }

    @Override
    public Set<Position> getAll() {
        return new HashSet<>(positionRepository.findAll());
    }
}
