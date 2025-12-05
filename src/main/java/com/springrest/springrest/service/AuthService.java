package com.springrest.springrest.service;

import com.springrest.springrest.dto.request.LoginRequest;
import com.springrest.springrest.dto.request.RegisterRequest;
import com.springrest.springrest.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String token);
}
