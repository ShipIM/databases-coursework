package com.example.service;

import com.example.exception.CalculateSelfpriceException;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Item;
import com.example.model.entity.ItemsForPeriod;
import com.example.repository.ItemRepository;
import com.example.repository.ItemsForPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final DetailsService detailsService;
    private final ItemsForPeriodRepository itemsForPeriodRepository;

    public Page<Item> getItems(String name, String category, Pageable pageable) {
        long total = itemRepository.countFilteredItems(name, category);
        List<Item> items = itemRepository.findFilteredItems(
                name,
                category,
                pageable.getPageSize(),
                pageable.getPageNumber());

        return new PageImpl<>(items, pageable, total);
    }

    public Item getItem(long id) {
        return itemRepository.findItem(id)
                .orElseThrow(() -> new EntityNotFoundException("Предмета с таким идентификатором не существует"));
    }

    public Page<Item> getFavouriteItems(String email, String name, String category, Pageable pageable) {
        if (!detailsService.isUserExists(email)) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        long total = itemRepository.countFavouriteItems(email, name, category);
        List<Item> items = itemRepository.findFavouriteItems(
                email,
                name,
                category,
                pageable.getPageSize(),
                pageable.getPageNumber());

        return new PageImpl<>(items, pageable, total);
    }

    public boolean isFavourite(String email, long id) {
        if (!detailsService.isUserExists(email)) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }
        if (!this.isItemExists(id)) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        return itemRepository.isFavourite(email, id);
    }

    public void addFavouriteItem(String username, long id) {
        if (!detailsService.isUserExists(username)) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }
        if (!this.isItemExists(id)) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        itemRepository.addFavouriteItem(username, id);
    }

    public void deleteFavouriteItem(String username, long id) {
        if (!detailsService.isUserExists(username)) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }
        if (!this.isItemExists(id)) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        itemRepository.deleteFavouriteItem(username, id);
    }

    public boolean isItemExists(long id) {
        return itemRepository.isItemExists(id);
    }


    public long getSelfprice(long id) {
        if (!isItemExists(id)) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }
        return itemRepository.getSelfprice(id).orElseThrow(() ->
                new CalculateSelfpriceException("Невозможно подсчитать себестоимость"));
    }

    public List<ItemsForPeriod> getItemsForPeriod(LocalDate start, LocalDate end, long id) {
        if (!isItemExists(id)) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        return itemsForPeriodRepository.getItemsForPeriod(start, end, id).orElseThrow(() ->
                new NoSuchElementException("Нет продаж данного предмета за заданный промежуток времени"));
    }
}
