package com.springrest.springrest.service;

import com.springrest.springrest.dto.request.CourseRequest;
import com.springrest.springrest.dto.request.EnrollRequest;
import com.springrest.springrest.dto.response.CourseResponse;
import com.springrest.springrest.dto.response.EnrollResponse;

import java.util.List;

public interface EnrollService {
    EnrollResponse enrollUserToCourse(String token,Long courseId);
    List<EnrollResponse> findCoursesByUser(Long userId);
    String completedVideo(String token, Long enrollId , Long videoId);
    EnrollResponse userCourseProgress(String token,Long enrollId);
}
