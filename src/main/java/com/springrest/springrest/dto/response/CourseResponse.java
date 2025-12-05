package com.springrest.springrest.dto.response;

import com.springrest.springrest.entity.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String category;
    private Double price;
    private Level level;
    private String uploaderName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

