package com.springrest.springrest.service;

import com.springrest.springrest.dto.request.CourseRequest;
import com.springrest.springrest.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(String token, CourseRequest request);

    List<CourseResponse> getAllCourses();

    CourseResponse getCourse(Long id);

    CourseResponse updateCourse(String token, Long id, CourseRequest request);

    void deleteCourse(String token, Long id);

    List<CourseResponse> getUploadedCourses(String token);
}

