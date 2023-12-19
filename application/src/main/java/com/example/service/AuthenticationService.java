package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.createUser(user.getEmail(), user.getBirthDate(), user.getRegistrationDate(), user.getPassword());

        return user;
    }

    public User authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        return userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует"));
    }
}
