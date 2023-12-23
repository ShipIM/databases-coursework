package com.example.dto.itemsForPeriod;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ItemsForPeriodRequestDto {

    private LocalDate start;
    private LocalDate end;

    @NotBlank(message = "Идентификатор предмета не должен быть пустым")
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор предмета должен быть положительным числом типа long")
    @JsonProperty("item_id")
    private String itemId;

}
