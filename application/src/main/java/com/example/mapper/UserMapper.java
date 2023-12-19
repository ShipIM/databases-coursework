package com.example.mapper;

import com.example.dto.authentication.AuthenticationRequestDto;
import com.example.dto.authentication.RegistrationRequestDto;
import com.example.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(AuthenticationRequestDto dto);

    User mapToUser(RegistrationRequestDto dto);
}
