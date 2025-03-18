package com.slippery.lmsexample.repository;

import com.slippery.lmsexample.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
