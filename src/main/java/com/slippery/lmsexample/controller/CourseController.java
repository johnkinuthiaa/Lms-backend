package com.slippery.lmsexample.controller;

import com.slippery.lmsexample.dto.CourseDto;
import com.slippery.lmsexample.models.Course;
import com.slippery.lmsexample.service.CourseService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@CrossOrigin
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @PostMapping("/{adminId}/create")
    public ResponseEntity<CourseDto> createNewCourse(@PathVariable Long adminId, @RequestBody Course course) {
        var createdCourse =courseService.createNewCourse(adminId, course);
        return ResponseEntity.status(HttpStatusCode.valueOf(createdCourse.getStatusCode())).body(createdCourse);
    }
    @PutMapping("/{userId}/enroll/{courseId}")
    public ResponseEntity<CourseDto> enrollUserToCourse(@PathVariable Long userId,@PathVariable Long courseId){
        var enrolledUser =courseService.enrollUserToCourse(userId, courseId);
        return ResponseEntity.status(HttpStatusCode.valueOf(enrolledUser.getStatusCode())).body(enrolledUser);
    }
    @PutMapping("/{userId}/un-enroll/{courseId}")
    public ResponseEntity<CourseDto> unEnrollUserToCourse(@PathVariable Long userId,@PathVariable Long courseId){
        var unEnrolledUser =courseService.unEnrollUserToCourse(userId, courseId);
        return ResponseEntity.status(HttpStatusCode.valueOf(unEnrolledUser.getStatusCode())).body(unEnrolledUser);
    }
    @GetMapping("/all")
    public ResponseEntity<CourseDto> findAllCourses(){
        var allCourses=courseService.findAllCourses();
        return ResponseEntity.status(HttpStatusCode.valueOf(allCourses.getStatusCode())).body(allCourses);
    }
    @GetMapping("/{courseId}/get")
    public ResponseEntity<CourseDto> findCourseById(@PathVariable Long courseId){
        var course =courseService.findCourseById(courseId);
        return ResponseEntity.status(HttpStatusCode.valueOf(course.getStatusCode())).body(course);
    }
    @GetMapping("/course-map")
    public ResponseEntity<CourseDto> findAvailableCourse(){
        var courseHashMap =courseService.findAvailableCourse();
        return ResponseEntity.status(HttpStatusCode.valueOf(courseHashMap.getStatusCode())).body(courseHashMap);
    }
    @GetMapping("/get/{userId}/courses")
    public ResponseEntity<CourseDto> allCoursesEnrolledByUser(@PathVariable Long userId){
        var allUserCoursesEnrolledByUser =courseService.findAllCoursesEnrolledByUser(userId);
        return ResponseEntity.status(
                HttpStatusCode.valueOf(
                        allUserCoursesEnrolledByUser.getStatusCode()))
                .body(allUserCoursesEnrolledByUser);
    }
}
