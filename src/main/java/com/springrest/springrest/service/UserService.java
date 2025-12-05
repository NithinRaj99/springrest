package com.springrest.springrest.service;

import com.springrest.springrest.dto.response.UserProfileResponse;
import com.springrest.springrest.entity.Course;

import java.util.List;

public interface UserService {

    UserProfileResponse getProfile(String token);
    UserProfileResponse updateProfile(String token, UserProfileResponse request);
    List<Course> getEnrolledCourses(String token);
    List<Course> getUploadedCourses(String token);
}
