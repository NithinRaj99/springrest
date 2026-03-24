package com.springrest.springrest.repository;

import com.springrest.springrest.dto.response.CourseResponse;
import com.springrest.springrest.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByUploader(Long uploader, Pageable pageable);

}

