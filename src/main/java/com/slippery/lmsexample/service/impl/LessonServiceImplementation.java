package com.slippery.lmsexample.service.impl;

import com.slippery.lmsexample.dto.LessonDto;
import com.slippery.lmsexample.models.Lesson;
import com.slippery.lmsexample.repository.LessonRepository;
import com.slippery.lmsexample.service.CourseModuleService;
import com.slippery.lmsexample.service.CourseService;
import com.slippery.lmsexample.service.LessonService;
import com.slippery.lmsexample.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LessonServiceImplementation implements LessonService {
    private final LessonRepository lessonRepository;
    private final UsersService usersService;
    private final CourseService courseService;
    private final CourseModuleService courseModuleService;

    public LessonServiceImplementation(LessonRepository lessonRepository, UsersService usersService, CourseService courseService, CourseModuleService courseModuleService) {
        this.lessonRepository = lessonRepository;
        this.usersService = usersService;
        this.courseService = courseService;
        this.courseModuleService = courseModuleService;
    }

    @Override
    public LessonDto createNewLesson(Long tutorId, Long courseId, Long moduleId, Lesson lessonDetails) {
        LessonDto response =new LessonDto();
        var existingTutor =usersService.findUserAccountById(tutorId);
        var existingCourse =courseService.findCourseById(courseId);
        var existingModule =courseModuleService.findModuleById(moduleId);
        if(existingCourse.getStatusCode() !=200){
            response.setMessage("Course with id "+existingCourse+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        if(existingTutor.getStatusCode() !=200){
            response.setMessage("Course with id "+existingCourse+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        if(existingModule.getStatusCode() !=200){
            response.setMessage("Course with id "+existingCourse+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        if(!existingCourse.getCourse().getModulesInCourse().contains(existingModule.getModule())){
            response.setMessage("Module is not in the course "+existingCourse.getCourse().getTitle());
            response.setStatusCode(403);
            return response;
        }
        if(!Objects.equals(existingCourse.getCourse().getInstructorDetails().getId(), tutorId)){
            response.setMessage("Module tutor does not match the one provided ");
            response.setStatusCode(403);
            return response;
        }
        lessonDetails.setSlug(lessonDetails.getTitle().replace(" ","-"));
        lessonDetails.setContent(lessonDetails.getContent());
        lessonDetails.setCourseModule(existingModule.getModule());
        existingModule.getModule().getLessonsInModule().add(lessonDetails);
        lessonRepository.save(lessonDetails);
        response.setMessage("New lesson "+lessonDetails.getTitle()+" has been added to the module "+existingModule.getModule().getTitle());
        response.setStatusCode(201);
        return response;
    }
}
