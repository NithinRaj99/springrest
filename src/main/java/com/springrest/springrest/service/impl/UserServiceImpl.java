package com.springrest.springrest.service.impl;

import com.springrest.springrest.dto.response.UserProfileResponse;
import com.springrest.springrest.entity.Course;
import com.springrest.springrest.entity.User;
import com.springrest.springrest.repository.UserRepository;
import com.springrest.springrest.security.jwt.JwtService;
import com.springrest.springrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private User getUserFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        String email = jwtService.extractEmail(jwt);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public UserProfileResponse getProfile(String token) {
        User user = getUserFromToken(token);

        UserProfileResponse res = new UserProfileResponse();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setUsername(user.getUsername());
        res.setRole(user.getRole().name());

        return res;
    }

    @Override
    public UserProfileResponse updateProfile(String token, UserProfileResponse request) {
        User user = getUserFromToken(token);

        user.setUsername(request.getUsername());
        userRepository.save(user);

        return getProfile(token);
    }

    @Override
    public List<Course> getEnrolledCourses(String token) {
        User user = getUserFromToken(token);
        return user.getEnrollments()
                .stream()
                .map(e -> e.getCourse())
                .toList();
    }

    @Override
    public List<Course> getUploadedCourses(String token) {
        User user = getUserFromToken(token);
        return user.getUploadedCourses();
    }
}
