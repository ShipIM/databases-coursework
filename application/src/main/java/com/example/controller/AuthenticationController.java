package com.example.controller;

import com.example.dto.authentication.AuthenticationRequestDto;
import com.example.dto.authentication.RegistrationRequestDto;
import com.example.mapper.UserMapper;
import com.example.model.entity.User;
import com.example.service.AuthenticationService;
import com.example.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "auth", description = "Контроллер для регистрации и авторизации")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    @Operation(description = "Зарегистрировать нового пользователя")
    public String register(@RequestBody @Valid RegistrationRequestDto request) {
        User user = userMapper.mapToUser(request);

        user = service.register(user);

        return jwtService.generateToken(user);
    }

    @PostMapping("/authentication")
    @Operation(description = "Авторизовать уже существующего пользователя")
    public String authenticate(@RequestBody @Valid AuthenticationRequestDto request) {
        User user = userMapper.mapToUser(request);

        user = service.authenticate(user);

        return jwtService.generateToken(user);
    }
}
