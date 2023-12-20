package com.example.dto.page;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {

    @Min(value = 0, message = "Номер страницы должен быть положительным числом")
    @Pattern(regexp = "\\d+", message = "Номер страницы должен быть числом")
    @JsonAlias(value = {"pageNumber", "page_number"})
    private String pageNumber;

    @Min(value = 1, message = "Размер страницы должен быть >= 1")
    @Max(value = 20, message = "Размер страницы должен быть <= 20")
    @Pattern(regexp = "\\d+", message = "Размер страницы должен быть числом")
    @JsonAlias(value = {"pageSize", "page_size"})
    private String pageSize;

    public PageRequest formPageRequest() {
        return PageRequest.of(
                Integer.parseInt(Optional.ofNullable(pageNumber).orElse("0")),
                Integer.parseInt(Optional.ofNullable(pageSize).orElse("20"))
        );
    }

}
