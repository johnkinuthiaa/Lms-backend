package com.slippery.lmsexample.service;

import com.slippery.lmsexample.dto.CourseDto;
import com.slippery.lmsexample.models.CourseModule;

public interface CourseModuleService {
    CourseDto createNewModule(Long tutorId, Long courseId, CourseModule courseModuleDetails);
}
