package com.slippery.lmsexample.controller;

import com.slippery.lmsexample.dto.CourseDto;
import com.slippery.lmsexample.dto.ModuleDto;
import com.slippery.lmsexample.models.CourseModule;
import com.slippery.lmsexample.service.CourseModuleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/modules")

@Tag(name = " module controller",description = "create,delete modules from courses")
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
    @PutMapping("/{moduleId}/remove-from/{courseId}")
    public ResponseEntity<CourseDto> removeModuleFromCourse(@RequestParam Long adminId,@PathVariable Long courseId,@PathVariable Long moduleId){
        var removedModule =service.removeModuleFromCourse(adminId, courseId, moduleId);
        return ResponseEntity.status(HttpStatusCode.valueOf(removedModule.getStatusCode())).body(removedModule);
    }
    @GetMapping("/{moduleId}/get")
    public ResponseEntity<ModuleDto> findModuleById(@PathVariable Long moduleId){
        var existingModule =service.findModuleById(moduleId);
        return ResponseEntity.status(HttpStatusCode.valueOf(existingModule.getStatusCode())).body(existingModule);
    }
    @GetMapping("/get-all")
    public ResponseEntity<ModuleDto> findAllModules(){
        var existingModule =service.findAllModules();
        return ResponseEntity.status(HttpStatusCode.valueOf(existingModule.getStatusCode())).body(existingModule);
    }
    @GetMapping("/modules-in/{courseId}")
    public ResponseEntity<ModuleDto> findAllModulesInCourse(@PathVariable Long courseId){
        var allModules =service.findAllModulesInCourse(courseId);
        return ResponseEntity.status(HttpStatusCode.valueOf(allModules.getStatusCode())).body(allModules);
    }
}
