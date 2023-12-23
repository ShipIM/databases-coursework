package com.example.mapper;

import com.example.dto.dependency.DependencyResponseDto;
import com.example.model.entity.Dependency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DependencyMapper {

    DependencyResponseDto mapToDto(Dependency dependency);
}
