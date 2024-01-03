package com.example.dto.dependency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DependencyResponseDto {

    private String name;

    private Long id;

    private Long level;

}
