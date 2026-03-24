package com.springrest.springrest.repository;

import com.springrest.springrest.entity.Course;
import com.springrest.springrest.entity.CourseModule;
import com.springrest.springrest.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByModule(CourseModule module);

    @Query("""
    SELECT c
    FROM Video v
    JOIN v.module m
    JOIN m.course c
    WHERE v.id = :videoId
""")
    Course findCourseIdsByVideoId(Long videoId);

    long countByModule_Course_Id(Long courseId);
}
