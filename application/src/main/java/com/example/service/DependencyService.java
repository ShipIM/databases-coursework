package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Dependency;
import com.example.repository.DependencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DependencyService {

    private final DependencyRepository dependencyRepository;
    private final ItemService itemService;

    public Page<Dependency> getDependencies(long id, Pageable pageable) {
        if (!itemService.isItemExists(id)) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        Long total = dependencyRepository.getDependenciesCount(id);
        List<Dependency> dependencyList = dependencyRepository.getDependencies(id, pageable.getPageSize(), pageable.getPageNumber());
        return new PageImpl<>(dependencyList, pageable, total);
    }
}
