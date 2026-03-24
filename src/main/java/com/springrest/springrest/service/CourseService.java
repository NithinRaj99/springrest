package com.springrest.springrest.service;

import com.springrest.springrest.dto.request.CourseRequest;
import com.springrest.springrest.dto.response.CourseResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(String token, CourseRequest request);

    Page<CourseResponse> getAllCourses(int page, int size);

    CourseResponse getCourse(Long id);

    CourseResponse updateCourse(String token, Long id, CourseRequest request);

    void deleteCourse(String token, Long id);

    Page<CourseResponse> getUploadedCourses(String token,int page,int size);

}

