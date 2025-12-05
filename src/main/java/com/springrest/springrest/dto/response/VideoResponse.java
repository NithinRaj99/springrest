package com.springrest.springrest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoResponse {
    private Long id;
    private String videoTitle;
    private String videoUrl;
    private Integer durationSeconds;
    private int videoOrder;
}

