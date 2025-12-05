package com.springrest.springrest.service;

import com.springrest.springrest.dto.request.VideoRequest;
import com.springrest.springrest.dto.response.VideoResponse;

import java.util.List;

public interface VideoService {

    VideoResponse uploadVideo(String token, Long moduleId, VideoRequest request);

    List<VideoResponse> getVideosByModule(Long moduleId);

    VideoResponse getVideo(Long id);

    VideoResponse updateVideo(String token, Long id, VideoRequest request);

    void deleteVideo(String token, Long id);
}

