package com.springrest.springrest.controller;

import com.springrest.springrest.dto.request.VideoRequest;
import com.springrest.springrest.dto.response.VideoResponse;
import com.springrest.springrest.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/modules/{moduleId}")
    public ResponseEntity<VideoResponse> uploadVideo(
            @RequestHeader("Authorization") String token,
            @PathVariable Long moduleId,
            @RequestBody VideoRequest request) {

        return ResponseEntity.ok(videoService.uploadVideo(token, moduleId, request));
    }

    @GetMapping("/modules/{moduleId}")
    public ResponseEntity<List<VideoResponse>> listVideos(
            @PathVariable Long moduleId) {

        return ResponseEntity.ok(videoService.getVideosByModule(moduleId));
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoResponse> getVideo(@PathVariable Long videoId) {
        return ResponseEntity.ok(videoService.getVideo(videoId));
    }

    @PutMapping("/{videoId}")
    public ResponseEntity<VideoResponse> updateVideo(
            @RequestHeader("Authorization") String token,
            @PathVariable Long videoId,
            @RequestBody VideoRequest request) {

        return ResponseEntity.ok(videoService.updateVideo(token, videoId, request));
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<String> deleteVideo(
            @RequestHeader("Authorization") String token,
            @PathVariable Long videoId) {

        videoService.deleteVideo(token, videoId);
        return ResponseEntity.ok("Video deleted successfully");
    }
}
