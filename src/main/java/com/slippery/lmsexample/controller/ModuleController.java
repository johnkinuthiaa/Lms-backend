package com.slippery.lmsexample.controller;

import com.slippery.lmsexample.dto.CourseDto;
import com.slippery.lmsexample.models.CourseModule;
import com.slippery.lmsexample.service.CourseModuleService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/modules")
public class ModuleController{
    private final CourseModuleService service;

    public ModuleController(CourseModuleService service) {
        this.service = service;
    }
    @PutMapping("/{tutorId}/new-module")
    public ResponseEntity<CourseDto> createNewModule(@PathVariable Long tutorId, @RequestParam Long courseId, @RequestBody CourseModule courseModuleDetails) {
        var createdModule =service.createNewModule(tutorId, courseId, courseModuleDetails);
        return ResponseEntity.status(HttpStatusCode.valueOf(createdModule.getStatusCode())).body(createdModule);
    }
}
