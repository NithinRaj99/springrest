package com.springrest.springrest.dto.request;

import com.springrest.springrest.entity.enums.Level;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnrollRequest {

    private LocalDateTime enrolledAt;
    private int progress;
    private Long courseId;
    private Long userId;
}