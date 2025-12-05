package com.springrest.springrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoTitle;

    private String videoUrl;

    private Integer durationSeconds;

    private int videoOrder;

    @ManyToOne
    @JoinColumn(name = "module_id")
    @JsonIgnore
    private CourseModule module;
}

