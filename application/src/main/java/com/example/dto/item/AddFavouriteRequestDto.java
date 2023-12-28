package com.example.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFavouriteRequestDto {

    @NotBlank(message = "The item ID must not be empty")
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "The item ID must be a positive number of type long")
    @JsonProperty("item_id")
    private String itemId;

}
