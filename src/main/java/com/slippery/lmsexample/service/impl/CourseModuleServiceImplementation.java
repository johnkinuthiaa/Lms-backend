package com.slippery.lmsexample.service.impl;

import com.slippery.lmsexample.dto.CourseDto;
import com.slippery.lmsexample.models.CourseModule;
import com.slippery.lmsexample.repository.ModuleRepository;
import com.slippery.lmsexample.service.CourseModuleService;
import com.slippery.lmsexample.service.CourseService;
import com.slippery.lmsexample.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CourseModuleServiceImplementation implements CourseModuleService {
    private final ModuleRepository repository;
    private final UsersService usersService;
    private final CourseService courseService;

    public CourseModuleServiceImplementation(ModuleRepository repository, UsersService usersService, CourseService courseService) {
        this.repository = repository;
        this.usersService = usersService;
        this.courseService = courseService;
    }

    @Override
    public CourseDto createNewModule(Long tutorId, Long courseId, CourseModule courseModuleDetails) {
        CourseDto response =new CourseDto();
        var course =courseService.findCourseById(courseId);
        var user =usersService.findUserAccountById(tutorId);
        if(course.getStatusCode() !=200){
            return course;
        }
        if(user.getStatusCode() !=200){
            response.setMessage(user.getMessage());
            response.setStatusCode(user.getStatusCode());
            return response;
        }
        courseModuleDetails.setCourse(course.getCourse());
        courseModuleDetails.setLessonsInModule(new ArrayList<>());
        courseModuleDetails.setTutor(user.getUser());

        var modulesInCourse =course.getCourse().getModulesInCourse();
        modulesInCourse.add(courseModuleDetails);
        course.getCourse().setModulesInCourse(modulesInCourse);
        repository.save(courseModuleDetails);
        response.setMessage("New module for "+course.getCourse().getTitle()+" has been added");
        response.setStatusCode(201);
        return response;
    }
}
