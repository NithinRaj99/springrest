package com.springrest.springrest.controller;


import com.springrest.springrest.dto.request.ModuleRequest;
import com.springrest.springrest.entity.CourseModule;
import com.springrest.springrest.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    // CREATE module for a course
    @PreAuthorize("hasAnyRole('UPLOADER', 'ADMIN')")
    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<CourseModule> createModule(
            @PathVariable Long courseId,
            @RequestBody ModuleRequest request) {

        CourseModule module = moduleService.createModule(courseId, request);
        return ResponseEntity.ok(module);
    }

    // LIST modules for a course
    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<CourseModule>> getModulesByCourse(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(moduleService.getModulesByCourse(courseId));
    }

    // GET module details
    @GetMapping("/modules/{moduleId}")
    public ResponseEntity<CourseModule> getModuleById(@PathVariable Long moduleId) {
        return ResponseEntity.ok(moduleService.getModuleById(moduleId));
    }

    // UPDATE module
    @PreAuthorize("hasAnyRole('UPLOADER', 'ADMIN')")
    @PutMapping("/modules/{moduleId}")
    public ResponseEntity<CourseModule> updateModule(
            @PathVariable Long moduleId,
            @RequestBody ModuleRequest request) {

        CourseModule updated = moduleService.updateModule(moduleId, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE module
    @PreAuthorize("hasAnyRole('UPLOADER', 'ADMIN')")
    @DeleteMapping("/modules/{moduleId}")
    public ResponseEntity<String> deleteModule(@PathVariable Long moduleId) {
        moduleService.deleteModule(moduleId);
        return ResponseEntity.ok("Module deleted successfully");
    }
}
