package com.slippery.lmsexample.repository;

import com.slippery.lmsexample.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
}
