package com.springrest.springrest.controller;

import com.springrest.springrest.dto.request.EnrollRequest;
import com.springrest.springrest.dto.response.EnrollResponse;
import com.springrest.springrest.service.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    EnrollService enrollService;

    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<EnrollResponse> enrollUserToCourse(
            @RequestHeader("Authorization") String token,
            @PathVariable Long courseId) {

        return ResponseEntity.ok(enrollService.enrollUserToCourse(token,courseId));
    }

    @GetMapping("/user/enrollments")
    public ResponseEntity<List<EnrollResponse>> userEnrolledCourses(Authentication authentication) {
        Long userId= (Long)authentication.getPrincipal();
        return ResponseEntity.ok(enrollService.findCoursesByUser(userId));
    }

    @PostMapping("/{enrollId}/complete-video/{videoId}")
    public String completedVideo(
            @RequestHeader("Authorization") String token,
            @PathVariable Long enrollId,
            @PathVariable Long videoId) {

        return enrollService.completedVideo(token,enrollId,videoId);
    }

}
