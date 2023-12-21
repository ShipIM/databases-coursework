package com.example.controller;

import com.example.dto.item.ItemResponseDto;
import com.example.dto.item.ItemViewResponseDto;
import com.example.dto.page.PageRequestDto;
import com.example.mapper.ItemMapper;
import com.example.model.entity.Item;
import com.example.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Page<ItemViewResponseDto> getItems(
            String category,
            String name,
            @Valid PageRequestDto pageRequestDto
    ) {
        Page<Item> items = itemService.getItems(name, category, pageRequestDto.formPageRequest());

        return items.map(itemMapper::mapToViewDto);
    }

    @GetMapping("/{id}")
    public ItemResponseDto getItem(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор предмета должен быть положительным числом типа long")
            String id) {
        long itemId = Long.parseLong(id);
        Item item = itemService.getItem(itemId);

        ItemResponseDto responseItem = itemMapper.mapToDto(item);

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            responseItem.setFavourite(itemService.isFavourite(user.getUsername(), itemId));
        }

        return responseItem;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/favourites")
    public Page<ItemViewResponseDto> getFavouriteItems(
            String name,
            String category,
            @Valid PageRequestDto pageRequestDto) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Item> items = itemService
                .getFavouriteItems(user.getUsername(), name, category, pageRequestDto.formPageRequest());

        return items.map(itemMapper::mapToViewDto);
    }

}
