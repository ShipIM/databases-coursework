package com.example.controller;

import com.example.dto.item.AddFavouriteRequestDto;
import com.example.dto.item.ItemResponseDto;
import com.example.dto.item.ItemViewResponseDto;
import com.example.dto.itemsForPeriod.ItemsForPeriodRequestDto;
import com.example.dto.page.PageRequestDto;
import com.example.mapper.ItemMapper;
import com.example.model.entity.Item;
import com.example.model.entity.ItemsForPeriod;
import com.example.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "items", description = "Контроллер для управления предметами")
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    @GetMapping
    @Operation(description = "Получить все существующие предметы с возможной фильтрацией по категории и названию")
    public Page<ItemViewResponseDto> getItems(
            String category,
            String name,
            @Valid PageRequestDto pageRequestDto
    ) {
        Page<Item> items = itemService.getItems(name, category, pageRequestDto.formPageRequest());

        return items.map(itemMapper::mapToViewDto);
    }

    @GetMapping("/{id}")
    @Operation(description = "Получить предмет по идентификатору")
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
    @Operation(description = "Получить список избранных предметов пользователя")
    public Page<ItemViewResponseDto> getFavouriteItems(
            String name,
            String category,
            @Valid PageRequestDto pageRequestDto) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Item> items = itemService
                .getFavouriteItems(user.getUsername(), name, category, pageRequestDto.formPageRequest());

        return items.map(itemMapper::mapToViewDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/favourites")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(description = "Добавить предмет в избранные")
    public void addFavouriteItem(@RequestBody @Valid AddFavouriteRequestDto dto) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        itemService.addFavouriteItem(user.getUsername(), Long.parseLong(dto.getItemId()));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/favourites/{id}")
    @Operation(description = "Удалить предмет из избранных")
    public void deleteFavouriteItem(@PathVariable("id") String id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        itemService.deleteFavouriteItem(user.getUsername(), Long.parseLong(id));
    }

    @GetMapping("/selfprice/{id}")
    @Operation(description = "Получить себестоимость предмета исходя из его компонентов")
    public long getSelfprice(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор предмета должен быть положительным числом типа long")
            String id) {
        return itemService.getSelfprice(Long.parseLong(id));
    }

    @PostMapping("/items-for-period")
    @Operation(description = "Получить максимальную цену товара за день на заданном промежутке")
    public List<ItemsForPeriod> getItemsForPeriod(@RequestBody @Valid ItemsForPeriodRequestDto dto) {
        return itemService.getItemsForPeriod(dto.getStart(), dto.getEnd(), Long.parseLong(dto.getItemId()));
    }
}
