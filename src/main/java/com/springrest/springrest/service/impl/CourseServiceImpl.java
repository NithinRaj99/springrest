package com.springrest.springrest.service.impl;

import com.springrest.springrest.dto.UserData;
import com.springrest.springrest.dto.request.CourseRequest;
import com.springrest.springrest.dto.response.CourseResponse;
import com.springrest.springrest.entity.Course;
import com.springrest.springrest.entity.CourseCategory;
import com.springrest.springrest.entity.enums.Role;
import com.springrest.springrest.repository.CourseRepository;
import com.springrest.springrest.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.springrest.springrest.service.CourseService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final JwtService jwtService;

    private UserData getUserFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        UserData User = new UserData();
        if(!jwtService.validateToken(jwt)){
           throw new RuntimeException("invalid token");
        }

        User.setUserId(jwtService.extractUserId(jwt));
        User.setEmail(jwtService.extractEmail(jwt));
        User.setRole(jwtService.extractRole(jwt));
        User.setUserName(jwtService.extractUserName(jwt));


        return User;
    }

    private CourseResponse convert(Course c) {
        return new CourseResponse(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getThumbnailUrl(),
                c.getCategory().getName(),
                c.getPrice(),
                c.getLevel(),
                c.getUploaderName(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }

    @Override
    public CourseResponse createCourse(String token, CourseRequest req) {

        UserData uploader = getUserFromToken(token);

        if (uploader.getRole() != Role.UPLOADER)
            throw new RuntimeException("Only uploader can create courses");


        Course course = new Course();
        CourseCategory category = new CourseCategory();
        category.setId(req.getCategory());
        course.setUploader(uploader.getUserId());
        course.setTitle(req.getTitle());
        course.setDescription(req.getDescription());
        course.setThumbnailUrl(req.getThumbnailUrl());
        course.setCategory(category);
        course.setPrice(req.getPrice());
        course.setLevel(req.getLevel());
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        course.setUploaderName(uploader.getUserName());

        return convert(courseRepository.save(course));
    }

    @Override
    public Page<CourseResponse> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable)
                .map(this::convert);
    }

    @Override
    public CourseResponse getCourse(Long id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return convert(c);
    }

    @Override
    public CourseResponse updateCourse(String token, Long id, CourseRequest req) {

        UserData uploader = getUserFromToken(token);

        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!c.getUploader().equals(uploader.getUserId()))
            throw new RuntimeException("You can update only your own courses");


        CourseCategory category = new CourseCategory();
        category.setId(req.getCategory());
        c.setTitle(req.getTitle());
        c.setDescription(req.getDescription());
        c.setThumbnailUrl(req.getThumbnailUrl());
        c.setCategory(category);
        c.setPrice(req.getPrice());
        c.setLevel(req.getLevel());
        c.setUpdatedAt(LocalDateTime.now());

        return convert(courseRepository.save(c));
    }

    @Override
    public void deleteCourse(String token, Long id) {

        UserData uploader = getUserFromToken(token);

        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!c.getUploader().equals(uploader.getUserId()))
            throw new RuntimeException("You can delete only your own courses");

        courseRepository.delete(c);
    }

    @Override
    public Page<CourseResponse> getUploadedCourses(String token,int page,int size) {
        UserData uploader = getUserFromToken(token);
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findByUploader(uploader.getUserId(),pageable)
                .map(this::convert);
    }

}
