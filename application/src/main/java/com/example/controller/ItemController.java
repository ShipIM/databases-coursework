package com.example.controller;

import com.example.dto.item.ItemResponseDto;
import com.example.dto.page.PageRequestDto;
import com.example.mapper.ItemMapper;
import com.example.model.entity.Item;
import com.example.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "items", description = "Контроллер для управления предметами")
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @GetMapping
    public Page<ItemResponseDto> getItems(
            String category,
            String name,
            @Valid PageRequestDto pageRequestDto
    ) {
        Page<Item> items = itemService.getItems(name, category, pageRequestDto.formPageRequest());

        return items.map(itemMapper::mapToDto);
    }
}
