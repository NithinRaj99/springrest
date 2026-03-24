package com.springrest.springrest.service.impl;


import com.springrest.springrest.dto.UserData;
import com.springrest.springrest.dto.request.EnrollRequest;
import com.springrest.springrest.dto.response.EnrollResponse;
import com.springrest.springrest.entity.*;
import com.springrest.springrest.repository.*;
import com.springrest.springrest.service.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.springrest.springrest.security.jwt.JwtService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService {

    private final JwtService jwtService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final VideoRepository videoRepository;
    private final CompletedVideoRepository completedVideoRepository;

    private EnrollResponse convert(Enrollment e) {
        return new EnrollResponse(
                e.getId(),
                e.getEnrolledAt(),
                e.getCourse().getId(),
                e.getUser(),
                e.getCourse().getTitle(),
                e.getCourse().getThumbnailUrl(),
                e.getProgress()
        );
    }


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
    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
    public Video getVideo(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
    }
    public Enrollment getEnrollment(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
    }

    @Override
    public EnrollResponse enrollUserToCourse(String token,Long courseId) {

        UserData uploader=getUserFromToken(token);
        Course course = getCourse(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollment.setCourse(course);
        enrollment.setUser(uploader.getUserId());
        enrollment.setProgress(0);


        return convert(enrollmentRepository.save(enrollment));
    }

    @Override
    public List<EnrollResponse> findCoursesByUser(Long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUser(userId);
        List<EnrollResponse> enrollResponses = new ArrayList<>();
        for(Enrollment e : enrollments ){
            enrollResponses.add(convert(e));
        }
        return enrollResponses;
    }

    @Override
    public String completedVideo(String token, Long enrollId, Long videoId) {
        UserData uploader=getUserFromToken(token);
        Video video= getVideo(videoId);
        Course course = videoRepository.findCourseIdsByVideoId(videoId);
        long totalVideoCount = videoRepository.countByModule_Course_Id(course.getId());
        CompletedVideo completedVideo = new CompletedVideo();
        completedVideo.setUser(uploader.getUserId());
        completedVideo.setVideo(video);
        completedVideo.setCourse(course);
        completedVideo.setCompletedAt(LocalDateTime.now());
        completedVideoRepository.save(completedVideo);
        long completedVideoCount = completedVideoRepository.countByUserAndCourse_Id(uploader.getUserId(), course.getId());
        double completedPercent = (completedVideoCount * 100.0) / totalVideoCount;
        Enrollment enrollment=getEnrollment(enrollId);
        enrollment.setProgress((int) completedPercent);
        enrollmentRepository.save(enrollment);

        return video.getVideoTitle()+" marked as completed from "+course.getTitle()+" course";
    }

    @Override
    public EnrollResponse userCourseProgress(String token,Long enrollId) {
        UserData user = getUserFromToken(token);
        Enrollment enrollment = getEnrollment(enrollId);
        return convert(enrollment);
    }
}
