package com.example.dto.lot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LotResponseDto {

    @JsonProperty("lot_id")
    private long lotId;

    @JsonProperty("vendor")
    private String userLogin;

    @JsonProperty("cost_current")
    private long costCurrent;

    @JsonProperty("cost_buy")
    private long costBuy;

    @JsonProperty("time_end")
    private LocalDateTime timeEnd;

}
