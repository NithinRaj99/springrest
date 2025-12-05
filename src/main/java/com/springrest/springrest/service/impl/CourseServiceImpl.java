package com.springrest.springrest.service.impl;

import com.springrest.springrest.dto.request.CourseRequest;
import com.springrest.springrest.dto.response.CourseResponse;
import com.springrest.springrest.entity.Course;
import com.springrest.springrest.entity.User;
import com.springrest.springrest.entity.enums.Role;
import com.springrest.springrest.repository.CourseRepository;
import com.springrest.springrest.repository.UserRepository;
import com.springrest.springrest.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.springrest.springrest.service.CourseService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private User getUserFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        String email = jwtService.extractEmail(jwt);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private CourseResponse convert(Course c) {
        return new CourseResponse(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getThumbnailUrl(),
                c.getCategory(),
                c.getPrice(),
                c.getLevel(),
                c.getUploader().getUsername(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }

    @Override
    public CourseResponse createCourse(String token, CourseRequest req) {

        User uploader = getUserFromToken(token);

        if (uploader.getRole() != Role.UPLOADER)
            throw new RuntimeException("Only uploaders can create courses");

        Course course = new Course();
        course.setUploader(uploader);
        course.setTitle(req.getTitle());
        course.setDescription(req.getDescription());
        course.setThumbnailUrl(req.getThumbnailUrl());
        course.setCategory(req.getCategory());
        course.setPrice(req.getPrice());
        course.setLevel(req.getLevel());
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());

        return convert(courseRepository.save(course));
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream().map(this::convert)
                .toList();
    }

    @Override
    public CourseResponse getCourse(Long id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return convert(c);
    }

    @Override
    public CourseResponse updateCourse(String token, Long id, CourseRequest req) {

        User uploader = getUserFromToken(token);

        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!c.getUploader().getId().equals(uploader.getId()))
            throw new RuntimeException("You can update only your own courses");

        c.setTitle(req.getTitle());
        c.setDescription(req.getDescription());
        c.setThumbnailUrl(req.getThumbnailUrl());
        c.setCategory(req.getCategory());
        c.setPrice(req.getPrice());
        c.setLevel(req.getLevel());
        c.setUpdatedAt(LocalDateTime.now());

        return convert(courseRepository.save(c));
    }

    @Override
    public void deleteCourse(String token, Long id) {

        User uploader = getUserFromToken(token);

        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!c.getUploader().getId().equals(uploader.getId()))
            throw new RuntimeException("You can delete only your own courses");

        courseRepository.delete(c);
    }

    @Override
    public List<CourseResponse> getUploadedCourses(String token) {
        User uploader = getUserFromToken(token);

        return courseRepository.findByUploader(uploader)
                .stream().map(this::convert)
                .toList();
    }
}
