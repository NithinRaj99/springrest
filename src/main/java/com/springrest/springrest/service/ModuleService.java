package com.springrest.springrest.service;

import com.springrest.springrest.dto.request.ModuleRequest;
import com.springrest.springrest.entity.CourseModule;

import java.util.List;

public interface ModuleService {
    CourseModule createModule(Long courseId, ModuleRequest request);
    List<CourseModule> getModulesByCourse(Long courseId);
    CourseModule getModuleById(Long moduleId);
    CourseModule updateModule(Long moduleId, ModuleRequest request);
    void deleteModule(Long moduleId);
}
