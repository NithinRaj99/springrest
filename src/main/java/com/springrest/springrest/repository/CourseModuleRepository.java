package com.springrest.springrest.repository;

import com.springrest.springrest.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
    List<CourseModule> findByCourseIdOrderByModuleOrderAsc(Long courseId);
}

