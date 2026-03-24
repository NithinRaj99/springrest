package com.springrest.springrest.service.impl;

import com.springrest.springrest.dto.request.ModuleRequest;
import com.springrest.springrest.entity.Course;
import com.springrest.springrest.entity.CourseModule;
import com.springrest.springrest.repository.CourseModuleRepository;
import com.springrest.springrest.repository.CourseRepository;
import com.springrest.springrest.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final CourseRepository courseRepository;
    private final CourseModuleRepository moduleRepository;

    @Override
    public CourseModule createModule(Long courseId, ModuleRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        CourseModule module = new CourseModule();
        module.setModuleTitle(request.getModuleTitle());
        module.setModuleOrder(request.getModuleOrder());
        module.setCourse(course);

        return moduleRepository.save(module);
    }

    @Override
    public List<CourseModule> getModulesByCourse(Long courseId) {
        return moduleRepository.findByCourse_IdOrderByModuleOrderAsc(courseId);
    }

    @Override
    public CourseModule getModuleById(Long moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));
    }

    @Override
    public CourseModule updateModule(Long moduleId, ModuleRequest request) {
        CourseModule module = getModuleById(moduleId);

        module.setModuleTitle(request.getModuleTitle());
        module.setModuleOrder(request.getModuleOrder());

        return moduleRepository.save(module);
    }

    @Override
    public void deleteModule(Long moduleId) {
        CourseModule module = getModuleById(moduleId);
        moduleRepository.delete(module);
    }
}

