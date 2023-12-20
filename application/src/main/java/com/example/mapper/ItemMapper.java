package com.example.mapper;

import com.example.dto.item.ItemResponseDto;
import com.example.model.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemResponseDto mapToDto(Item item);

}
