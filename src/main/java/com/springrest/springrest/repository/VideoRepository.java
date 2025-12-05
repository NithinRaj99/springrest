package com.springrest.springrest.repository;

import com.springrest.springrest.entity.CourseModule;
import com.springrest.springrest.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByModule(CourseModule module);
}
