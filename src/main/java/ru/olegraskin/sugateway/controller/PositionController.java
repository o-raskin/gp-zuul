package ru.olegraskin.sugateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.olegraskin.sugateway.dto.PositionDto;
import ru.olegraskin.sugateway.service.PositionService;

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
