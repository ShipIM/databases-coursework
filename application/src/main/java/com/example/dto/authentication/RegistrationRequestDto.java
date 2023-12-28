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

    @Email(message = "Invalid email")
    @NotBlank(message = "The email should not be empty")
    private String email;

    @Size(min = 8, max = 16, message = "Incorrect password size")
    @NotBlank(message = "The password must not be empty")
    private String password;

    @JsonProperty("birth_date")
    private Date birthDate;

}
