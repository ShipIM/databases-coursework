package com.example.controller;

import com.example.dto.dependency.DependencyResponseDto;
import com.example.mapper.DependencyMapper;
import com.example.model.entity.Dependency;
import com.example.service.DependencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "dependencies", description = "Контроллер для получения зависимостей предмета")
@RequestMapping("dependencies")
@RequiredArgsConstructor
public class DependencyController {

    private final DependencyService dependencyService;
    private final DependencyMapper dependencyMapper;

    @GetMapping("/{id}")
    @Operation(description = "Получить зависимости предмета")
    public List<DependencyResponseDto> getDependencies(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$", message = "Идентификатор предмета должен быть положительным числом типа long")
            String id) {
        List<Dependency> dependencyList = dependencyService.getDependencies(Long.parseLong(id));

        return dependencyList.stream().map(dependencyMapper::mapToDto).collect(Collectors.toList());
    }
}
