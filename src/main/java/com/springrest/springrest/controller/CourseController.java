package com.springrest.springrest.controller;

import com.springrest.springrest.dto.request.CourseRequest;
import com.springrest.springrest.dto.response.CourseResponse;
import com.springrest.springrest.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(
            @RequestHeader("Authorization") String token,
            @RequestBody CourseRequest request) {

        return ResponseEntity.ok(courseService.createCourse(token, request));
    }

    @GetMapping("/allCourses")
    public Page<CourseResponse> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return courseService.getAllCourses(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody CourseRequest request) {

        return ResponseEntity.ok(courseService.updateCourse(token, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {

        courseService.deleteCourse(token, id);
        return ResponseEntity.ok("Course deleted successfully");
    }

    @GetMapping("/uploader/my-courses")
    public ResponseEntity<Page<CourseResponse>> myCourses(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(courseService.getUploadedCourses(token, page,size));
    }

}
