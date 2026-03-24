package com.springrest.springrest.service.impl;

import com.springrest.springrest.dto.UserData;
import com.springrest.springrest.security.jwt.JwtService;
import com.springrest.springrest.service.TestAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestAuthServiceImpl implements TestAuthService {

    private final JwtService jwtService;

    @Override
    public UserData getUserFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        UserData User = new UserData();
        if(!jwtService.validateToken(jwt)){
            throw new RuntimeException("invalid token");
        }

        User.setUserId(jwtService.extractUserId(jwt));
        User.setEmail(jwtService.extractEmail(jwt));
        User.setRole(jwtService.extractRole(jwt));
        User.setUserName(jwtService.extractUserName(jwt));


        return User;
    }
}
