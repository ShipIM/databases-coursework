package com.example.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegistrationRequestDto {

    @Email(message = "Невалидная почта")
    @NotBlank(message = "Почта не должна быть пустой")
    private String email;

    @Size(min = 8, max = 16, message = "Неправильный размер пароля")
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

    @JsonProperty("birth_date")
    private Date birthDate;

}
