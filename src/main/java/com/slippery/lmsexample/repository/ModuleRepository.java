package com.slippery.lmsexample.repository;

import com.slippery.lmsexample.models.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<CourseModule,Long> {
}
