package com.example.mapper;

import com.example.dto.lot.LotResponseDto;
import com.example.model.entity.Lot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotMapper {

    LotResponseDto mapToDto(Lot lot);
}
