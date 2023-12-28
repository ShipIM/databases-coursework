package com.example.controller;

import com.example.dto.dependency.DependencyResponseDto;
import com.example.dto.page.PageRequestDto;
import com.example.mapper.DependencyMapper;
import com.example.model.entity.Dependency;
import com.example.service.DependencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "dependencies", description = "The controller for getting the dependencies of the item")
@RequestMapping("/dependencies")
@RequiredArgsConstructor
public class DependencyController {

    private final DependencyService dependencyService;
    private final DependencyMapper dependencyMapper;

    @GetMapping("/{id}")
    @Operation(description = "Get item dependencies")
    public Page<DependencyResponseDto> getDependencies(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$", message = "The item ID must be a positive number of type long")
            String id,
            @Valid PageRequestDto pageRequestDto) {
        Page<Dependency> dependencyPage = dependencyService.getDependencies(Long.parseLong(id), pageRequestDto.formPageRequest());

        return dependencyPage.map(dependencyMapper::mapToDto);
    }
}
