package ru.olegraskin.sugateway.position.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.olegraskin.sugateway.position.dto.PositionDto;
import ru.olegraskin.sugateway.position.service.PositionService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public Set<PositionDto> getAllPositions() {
        return positionService.getAll().stream()
                .map(PositionDto::new)
                .collect(Collectors.toSet());
    }
}
