package com.example.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDto {

    private Integer id;
    private String name;
    private String properties;

    @JsonProperty("is_favourite")
    private boolean isFavourite;
}
