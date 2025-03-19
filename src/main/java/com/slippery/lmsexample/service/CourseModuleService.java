package com.slippery.lmsexample.service;

import com.slippery.lmsexample.dto.CourseDto;
import com.slippery.lmsexample.dto.ModuleDto;
import com.slippery.lmsexample.models.CourseModule;

public interface CourseModuleService {
    CourseDto createNewModule(Long tutorId, Long courseId, CourseModule courseModuleDetails);
    CourseDto removeModuleFromCourse(Long adminId,Long courseId, Long moduleId);
    ModuleDto findModuleById(Long moduleId);

    ModuleDto findAllModules();
    ModuleDto findAllModulesInCourse(Long courseId);
}
