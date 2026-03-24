package com.springrest.springrest.service.impl;

import com.springrest.springrest.dto.UserData;
import com.springrest.springrest.dto.request.VideoRequest;
import com.springrest.springrest.dto.response.VideoResponse;
import com.springrest.springrest.entity.CourseModule;
import com.springrest.springrest.entity.Video;
import com.springrest.springrest.repository.CourseModuleRepository;
import com.springrest.springrest.repository.VideoRepository;
import com.springrest.springrest.security.jwt.JwtService;
import com.springrest.springrest.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final CourseModuleRepository moduleRepository;
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

    private VideoResponse convert(Video v) {
        return new VideoResponse(
                v.getId(),
                v.getVideoTitle(),
                v.getVideoUrl(),
                v.getDurationSeconds(),
                v.getVideoOrder()
        );
    }

    @Override
    public VideoResponse uploadVideo(String token, Long moduleId, VideoRequest req) {

        UserData uploader = getUserFromToken(token);

        if (!uploader.getRole().name().equals("UPLOADER"))
            throw new RuntimeException("Only uploaders can upload videos");

        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        if (!module.getCourse().getUploader().equals(uploader.getUserId()))
            throw new RuntimeException("Cannot upload videos to another uploader's module");

        Video v = new Video();
        v.setVideoTitle(req.getVideoTitle());
        v.setVideoUrl(req.getVideoUrl());
        v.setDurationSeconds(req.getDurationSeconds());
        v.setVideoOrder(req.getVideoOrder());
        v.setModule(module);

        return convert(videoRepository.save(v));
    }

    @Override
    public List<VideoResponse> getVideosByModule(Long moduleId) {

        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        return videoRepository.findByModule(module)
                .stream().map(this::convert).toList();
    }

    @Override
    public VideoResponse getVideo(Long id) {
        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        return convert(v);
    }

    @Override
    public VideoResponse updateVideo(String token, Long id, VideoRequest req) {

        UserData uploader = getUserFromToken(token);

        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        if (!v.getModule().getCourse().getUploader().equals(uploader.getUserId()))
            throw new RuntimeException("You can update only your own videos");

        v.setVideoTitle(req.getVideoTitle());
        v.setVideoUrl(req.getVideoUrl());
        v.setDurationSeconds(req.getDurationSeconds());
        v.setVideoOrder(req.getVideoOrder());

        return convert(videoRepository.save(v));
    }

    @Override
    public void deleteVideo(String token, Long id) {

        UserData uploader = getUserFromToken(token);

        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        if (!v.getModule().getCourse().getUploader().equals(uploader.getUserId()))
            throw new RuntimeException("You can delete only your own videos");

        videoRepository.delete(v);
    }
}

