package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Lot;
import com.example.repository.LotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LotService {

    private final LotRepository lotRepository;
    private final ItemService itemService;

    public Page<Lot> getActiveLots(long id, Pageable pageable) {
        if (!itemService.isItemExists(id)) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        long total = lotRepository.countActiveLots(id);
        List<Lot> lots = lotRepository.findActiveLots(
                id,
                pageable.getPageSize(),
                pageable.getPageNumber());

        return new PageImpl<>(lots, pageable, total);
    }
}
