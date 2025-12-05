package com.springrest.springrest.service.impl;

import com.springrest.springrest.dto.request.LoginRequest;
import com.springrest.springrest.dto.request.RegisterRequest;
import com.springrest.springrest.dto.response.AuthResponse;
import com.springrest.springrest.entity.User;
import com.springrest.springrest.repository.UserRepository;
import com.springrest.springrest.security.jwt.JwtService;
import com.springrest.springrest.service.AuthService;
import com.springrest.springrest.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = request.getRole() != null ? Role.valueOf(request.getRole()) : Role.CONSUMER;
        if (!Role.isValid(request.getRole())) {
            throw new RuntimeException("Invalid role value!");
        }
        if (request.getRole().equalsIgnoreCase("ADMIN")) {
            String errorMessage = "User with Admin Role cannot be created";
            return new AuthResponse(errorMessage,"403",null,null)  ;
        }
        user.setRole(Role.from(request.getRole()));


        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken, user.getUsername(), user.getRole().name());
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken, user.getUsername(), user.getRole().name());
    }

    @Override
    public AuthResponse refreshToken(String token) {

        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtService.generateAccessToken(user);

        return new AuthResponse(newAccessToken, token, user.getUsername(), user.getRole().name());
    }
}
