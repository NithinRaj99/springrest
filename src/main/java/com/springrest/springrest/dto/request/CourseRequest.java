package com.springrest.springrest.dto.request;

import com.springrest.springrest.entity.enums.Level;
import lombok.Data;

@Data
public class CourseRequest {
    private String title;
    private String description;
    private String thumbnailUrl;
    private Long category;
    private Double price;
    private Level level;  // Beginner, Intermediate, Advanced
}
