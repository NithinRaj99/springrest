package com.springrest.springrest.controller;

import com.springrest.springrest.dto.UserData;
import com.springrest.springrest.service.CourseService;
import com.springrest.springrest.service.TestAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/validate")
@RequiredArgsConstructor
public class TestAuthController {

    private final TestAuthService testAuthService;

    @PostMapping("/userFromToken")
    public UserData getUserFromToken(@RequestBody Map<String, String> payload) {

        String token = payload.get("token");

       return testAuthService.getUserFromToken(token);
    }
}
