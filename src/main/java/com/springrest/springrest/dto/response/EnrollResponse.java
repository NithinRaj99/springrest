package com.springrest.springrest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EnrollResponse {
    private Long id;
    private LocalDateTime enrolledAt;
    private Long courseId;
    private Long userId;
    private String title;
    private String thumbnailUrl;
    private int progress;
}
