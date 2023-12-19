package com.example.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequestDto {

    @Email(message = "Невалидная почта")
    private String email;

    @Size(min = 8, max = 16, message = "Неправильный размер пароля")
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

}
