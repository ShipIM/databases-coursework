package com.example.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsForPeriod {

    private LocalDate day;
    private Long maxCostBuy;
    private Long quantity;

}
