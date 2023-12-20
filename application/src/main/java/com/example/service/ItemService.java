package com.example.service;

import com.example.model.entity.Item;
import com.example.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<Item> getItems(String name, String category, Pageable pageable) {
        long total = itemRepository.countFilteredItems(category);
        List<Item> items = itemRepository.findFilteredItems(
                name,
                category,
                pageable.getPageSize(),
                pageable.getPageNumber());

        return new PageImpl<>(items, pageable, total);
    }
}
