package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dependency {

    private Long id;
    private Long itemFirstId;
    private Long itemSecondId;

}
