package com.springrest.springrest.repository;

import com.springrest.springrest.entity.CompletedVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedVideoRepository extends JpaRepository<CompletedVideo,Long> {

    // CORRECT: 'User' (Long) + 'Course_Id' (Entity's ID)
    long countByUserAndCourse_Id(Long userId, Long courseId);
}
