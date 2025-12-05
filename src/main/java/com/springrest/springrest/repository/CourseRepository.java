package com.springrest.springrest.repository;

import com.springrest.springrest.entity.Course;
import com.springrest.springrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByUploader(User uploader);
}

