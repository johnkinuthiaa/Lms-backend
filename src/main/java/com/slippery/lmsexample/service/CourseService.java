package com.slippery.lmsexample.service;

import com.slippery.lmsexample.dto.CourseDto;
import com.slippery.lmsexample.models.Course;

public interface CourseService {
    CourseDto createNewCourse(Long adminId, Course course);
    CourseDto findCourseById(Long courseId);
    CourseDto deleteCourseById(Long adminId,Long courseId);
    CourseDto enrollUserToCourse(Long userId,Long courseId);
    CourseDto unEnrollUserToCourse(Long userId,Long courseId);
    CourseDto findAllCourses();

}
