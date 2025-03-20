package com.slippery.lmsexample.controller;

import com.slippery.lmsexample.dto.LessonDto;
import com.slippery.lmsexample.models.Lesson;
import com.slippery.lmsexample.service.LessonService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lessons")
@CrossOrigin
public class LessonController {
    private final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }
    @PostMapping("/create")
    public ResponseEntity<LessonDto> createNewLesson(@RequestParam Long tutorId
            ,@RequestParam  Long courseId
            ,@RequestParam  Long moduleId,@RequestBody Lesson lessonDetails){
        var createdLesson =service.createNewLesson(tutorId, courseId, moduleId, lessonDetails);
        return ResponseEntity.status(HttpStatusCode.valueOf(createdLesson.getStatusCode())).body(createdLesson);
    }
    @GetMapping("/{lessonId}/get")
    public ResponseEntity<LessonDto> findLessonById(@PathVariable Long lessonId){
        var lesson =service.findLessonById(lessonId);
        return ResponseEntity.status(HttpStatusCode.valueOf(lesson.getStatusCode())).body(lesson);
    }
    @GetMapping("/all-in/{moduleId}")
    public ResponseEntity<LessonDto> findAllLessonsInModule(@PathVariable Long moduleId){
        var allLessonsInModules =service.findAllLessonsInModule(moduleId);
        return ResponseEntity.status(HttpStatusCode.valueOf(allLessonsInModules.getStatusCode())).body(allLessonsInModules);
    }
}
