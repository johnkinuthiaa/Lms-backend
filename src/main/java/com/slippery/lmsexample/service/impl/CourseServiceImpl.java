package com.slippery.lmsexample.service.impl;

import com.slippery.lmsexample.dto.CourseDto;
import com.slippery.lmsexample.models.Course;
import com.slippery.lmsexample.models.Enrollment;
import com.slippery.lmsexample.repository.CourseRepository;
import com.slippery.lmsexample.repository.EnrollmentRepository;
import com.slippery.lmsexample.service.CourseService;
import com.slippery.lmsexample.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    private final UsersService usersService;
    private final EnrollmentRepository enrollmentRepository;

    public CourseServiceImpl(CourseRepository repository, UsersService usersService, EnrollmentRepository enrollmentRepository) {
        this.repository = repository;
        this.usersService = usersService;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public CourseDto createNewCourse(Long adminId, Course course) {
        CourseDto response =new CourseDto();
        var existingUser =usersService.findUserAccountById(adminId);
        if(existingUser.getStatusCode() !=200){
            response.setMessage(existingUser.getMessage());
            response.setStatusCode(existingUser.getStatusCode());
            return response;
        }
        if(!Objects.equals(existingUser.getUser().getRole(), "ADMIN")){
            response.setMessage("Cannot create a course for that user!!sorry");
            response.setStatusCode(403);
            return response;
        }
        existingUser.getUser().getCourseList().add(course);
        course.setEnrolledLearners(new ArrayList<>());
        course.setInstructorDetails(existingUser.getUser());
        course.setModulesInCourse(new ArrayList<>());
        course.setSlug(course.getTitle().replace(" ","-"));
        course.setPrice(0L);
        repository.save(course);
        response.setMessage("New course created");
        response.setStatusCode(201);
        response.setCourse(course);
        return response;
    }

    @Override
    public CourseDto findCourseById(Long courseId) {
        CourseDto response =new CourseDto();
        var existingCourse =repository.findById(courseId);
        if(existingCourse.isEmpty()){
            response.setMessage("Course with id "+courseId+" not found");
            response.setStatusCode(404);
            return response;
        }
        response.setCourse(existingCourse.get());
        response.setMessage("Course with id "+courseId);
        response.setStatusCode(200);
        return response;
    }

    @Override
    public CourseDto deleteCourseById(Long adminId, Long courseId) {
        CourseDto response =new CourseDto();
        var course =findCourseById(courseId);
        var user =usersService.findUserAccountById(adminId);
        if(course.getStatusCode() !=200){
           return course;
        }
        if(user.getStatusCode() !=200){
            response.setMessage(user.getMessage());
            response.setStatusCode(user.getStatusCode());
            return response;
        }
        var courseToDelete =course.getCourse();
        courseToDelete.setModulesInCourse(null);
        courseToDelete.setEnrolledLearners(null);
        courseToDelete.setInstructorDetails(null);
        repository.delete(course.getCourse());
        response.setMessage("Course with id"+courseId+" has been deleted");

        return null;
    }

    @Override
    public CourseDto enrollUserToCourse(Long userId, Long courseId) {
        CourseDto response =new CourseDto();
        var course =findCourseById(courseId);
        var user =usersService.findUserAccountById(userId);
        if(course.getStatusCode() !=200){
            return course;
        }
        if(user.getStatusCode() !=200){
            response.setMessage(user.getMessage());
            response.setStatusCode(user.getStatusCode());
            return response;
        }
        var userCourses =user.getUser().getCourseList();
        if(userCourses.contains(course.getCourse())){
            response.setMessage("User was already to the course with id "+courseId);
            response.setStatusCode(404);
            return response;
        }

        userCourses.add(course.getCourse());
        user.getUser().setCourseList(userCourses);

        var enrolledUsers =course.getCourse().getEnrolledLearners();
        enrolledUsers.add(user.getUser());
        course.getCourse().setEnrolledLearners(enrolledUsers);
        repository.save(course.getCourse());
        response.setStatusCode(200);
        response.setMessage("User "+user.getUser().getUsername()+" enrolled to the "+course.getCourse().getTitle()+" course.");
        Enrollment enrollment =new Enrollment();
        enrollment.setCourseEnrolled(course.getCourse());
        enrollment.setUser(user.getUser());
        enrollmentRepository.save(enrollment);
        return response;
    }

    @Override
    public CourseDto unEnrollUserToCourse(Long userId, Long courseId) {
        CourseDto response =new CourseDto();
        var course =findCourseById(courseId);
        var user =usersService.findUserAccountById(userId);
        if(course.getStatusCode() !=200){
            return course;
        }
        if(user.getStatusCode() !=200){
            response.setMessage(user.getMessage());
            response.setStatusCode(user.getStatusCode());
            return response;
        }

        var userCourses =user.getUser().getCourseList();
        var enrolledUsers =course.getCourse().getEnrolledLearners();
        if(!userCourses.contains(course.getCourse())){
            response.setMessage("User was not enrolled in the course");
            response.setStatusCode(404);
            return response;
        }
        userCourses.remove(course.getCourse());
        enrolledUsers.remove(user.getUser());
        course.getCourse().setEnrolledLearners(enrolledUsers);
        repository.save(course.getCourse());
        response.setMessage("User "+user.getUser().getUsername()+" un-enrolled from course "+course.getCourse().getTitle());
        response.setStatusCode(203);
        return response;
    }

    @Override
    public CourseDto findAllCourses() {
        CourseDto response =new CourseDto();
        var allCourses =repository.findAll().reversed();
        if(allCourses.isEmpty()){
            response.setMessage("Course list is empty at the moment");
            response.setStatusCode(203);
            return response;
        }
        response.setMessage("All our courses");
        response.setStatusCode(200);
        response.setCourseList(allCourses);
        return response;
    }


}
