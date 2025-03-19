package com.slippery.lmsexample.service.impl;

import com.slippery.lmsexample.dto.CourseDto;
import com.slippery.lmsexample.dto.ModuleDto;
import com.slippery.lmsexample.models.CourseModule;
import com.slippery.lmsexample.repository.CourseRepository;
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

    public CourseModuleServiceImplementation(ModuleRepository repository,
                                             UsersService usersService,
                                             CourseService courseService) {
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
        courseModuleDetails.setTutor(user.getUser());
        courseModuleDetails.setLessonsInModule(new ArrayList<>());

        var modulesInCourse=course.getCourse().getModulesInCourse();
        courseModuleDetails.setCourse(course.getCourse());
        modulesInCourse.add(courseModuleDetails);
        course.getCourse().setModulesInCourse(modulesInCourse);
        courseModuleDetails.setSlug(courseModuleDetails.getTitle().replace(" ","-"));

        repository.save(courseModuleDetails);
        response.setMessage("New module "+courseModuleDetails.getTitle()+" for "+course.getCourse().getTitle()+" has been added");
        response.setStatusCode(201);
        return response;
    }

    @Override
    public CourseDto removeModuleFromCourse(Long adminId,Long courseId, Long moduleId) {
        CourseDto response =new CourseDto();
        var course =courseService.findCourseById(courseId);
        var user =usersService.findUserAccountById(adminId);
        var module =repository.findById(moduleId);
        if(module.isEmpty()){
            response.setStatusCode(404);
            response.setMessage("Module with id "+moduleId+" was not found");
            return response;
        }
        if(course.getStatusCode() !=200){
            return course;
        }
        if(user.getStatusCode() !=200){
            response.setMessage(user.getMessage());
            response.setStatusCode(user.getStatusCode());
            return response;
        }
        if(!Objects.equals(user.getUser().getRole(), "ADMIN")){
            response.setStatusCode(403);
            response.setMessage("Could not remove the module because this action requires sudo power");
            return response;
        }
        java.util.List<CourseModule> moduleList =course.getCourse().getModulesInCourse();
        if(!Objects.equals(module.get().getCourse().getId(), courseId)){
            response.setStatusCode(403);
            response.setMessage("Could not remove the module because it does not belong to the course");
            return response;
        }
        if(!moduleList.contains(module.get())){
            response.setStatusCode(403);
            response.setMessage("Could not remove the module because it does not belong to the course");
            return response;
        }
        module.get().setCourse(null);
        module.get().setTutor(null);
        moduleList.remove(module.get());
        course.getCourse().setModulesInCourse(moduleList);
        repository.delete(module.get());
        response.setMessage("Module removed from course");
        response.setStatusCode(203);
        return response;
    }

    @Override
    public ModuleDto findModuleById(Long moduleId) {
        ModuleDto response =new ModuleDto();
        var existingModule =repository.findById(moduleId);
        if(existingModule.isEmpty()){
            response.setStatusCode(404);
            response.setMessage("No module with id "+moduleId+" was found");
            return response;
        }
        response.setStatusCode(200);
        response.setModule(existingModule.get());
        response.setMessage("module with id "+moduleId+" was found");
        return response;

    }

    @Override
    public ModuleDto findAllModules() {
        ModuleDto response =new ModuleDto();
        var allModules =repository.findAll().reversed();
        if(allModules.isEmpty()){
            response.setStatusCode(203);
            response.setMessage("No modules available");
            return response;
        }
        response.setModules(allModules);
        response.setStatusCode(200);
        response.setMessage("All modules found");
        return response;
    }

    @Override
    public ModuleDto findAllModulesInCourse(Long courseId) {
        ModuleDto response =new ModuleDto();
        var existingCourse =courseService.findCourseById(courseId);
        if(existingCourse.getStatusCode() !=200){
            response.setMessage(existingCourse.getMessage());
            response.setStatusCode(existingCourse.getStatusCode());
            return response;
        }

        var allModulesInCourse =repository.findAll().stream()
                .filter(courseModule -> courseModule.getCourse().getId().equals(existingCourse.getCourse().getId()))
                .toList();
        if(allModulesInCourse.isEmpty()){
            response.setMessage("No module is in the course");
            response.setStatusCode(203);
            return response;
        }
        response.setModules(allModulesInCourse);
        response.setStatusCode(200);
        response.setMessage("All modules in course "+existingCourse.getCourse().getTitle());
        return response;
    }
}
