package com.springrest.springrest.controller;

import com.springrest.springrest.dto.request.RegisterRequest;
import com.springrest.springrest.dto.response.AuthResponse;
import com.springrest.springrest.dto.response.UserProfileResponse;
import com.springrest.springrest.entity.Course;
import com.springrest.springrest.service.AuthService;
import com.springrest.springrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public String test() {
        return "Test return";
    }
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getProfile(token));
    }

    @PostMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@RequestBody UserProfileResponse request,@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.updateProfile(token,request));
    }
    @PostMapping("/enrolled-courses")
    public ResponseEntity<List<Course>> getEnrolledCourses(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getEnrolledCourses(token));
    }
    @PostMapping("/uploaded-courses")
    public ResponseEntity<List<Course>> getUploadedCourses(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUploadedCourses(token));
    }
}
