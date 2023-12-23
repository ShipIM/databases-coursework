package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Dependency;
import com.example.repository.DependencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DependencyService {

    private final DependencyRepository dependencyRepository;
    private final ItemService itemService;

    public List<Dependency> getDependencies(long id) {
        if (!itemService.isItemExists(id)) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        return dependencyRepository.getDependencies(id);
    }
}
