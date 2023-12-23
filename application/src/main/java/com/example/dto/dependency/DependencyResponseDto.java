package com.example.dto.dependency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DependencyResponseDto {

    @JsonProperty("item_name")
    private String name;

    @JsonProperty("item_id")
    private Long id;

    private Long level;

}
