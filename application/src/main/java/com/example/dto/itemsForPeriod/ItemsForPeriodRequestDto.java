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

    @NotBlank(message = "The item ID must not be empty")
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "The item ID must be a positive number of type long")
    @JsonProperty("item_id")
    private String itemId;

}
