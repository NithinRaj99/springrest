package com.springrest.springrest.dto.request;

import lombok.Data;

@Data
public class VideoRequest {
    private String videoTitle;
    private String videoUrl;
    private Integer durationSeconds;
    private int videoOrder;
}
