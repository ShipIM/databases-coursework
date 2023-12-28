package com.example.controller;

import com.example.dto.lot.LotResponseDto;
import com.example.dto.page.PageRequestDto;
import com.example.mapper.LotMapper;
import com.example.model.entity.Lot;
import com.example.service.LotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "lots", description = "A controller for managing lots")
@RequestMapping("/lots")
@RequiredArgsConstructor
public class LotController {

    private final LotService lotService;
    private final LotMapper lotMapper;

    @GetMapping("/{id}")
    @Operation(description = "Get active item lots")
    public Page<LotResponseDto> getActiveLots(@PathVariable("id") String id, @Valid PageRequestDto pageRequestDto) {
        Page<Lot> lots = lotService.getActiveLots(Long.parseLong(id), pageRequestDto.formPageRequest());

        return lots.map(lotMapper::mapToDto);
    }
}
