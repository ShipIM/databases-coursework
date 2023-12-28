package com.example.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequestDto {

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8, max = 16, message = "Incorrect password size")
    @NotBlank(message = "The password must not be empty")
    private String password;

}
