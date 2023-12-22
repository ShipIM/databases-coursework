package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lot {

    private long lotId;
    private String userLogin;
    private long costCurrent;
    private long costBuy;
    private LocalDateTime timeEnd;

}
