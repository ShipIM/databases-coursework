package com.example.dto.lot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LotResponseDto {

    @JsonProperty("lot_id")
    private long id;

    @JsonProperty("vendor")
    private String login;

    @JsonProperty("cost_current")
    private long current;

    @JsonProperty("cost_buy")
    private long buy;

    @JsonProperty("time_end")
    private LocalDateTime end;

}
