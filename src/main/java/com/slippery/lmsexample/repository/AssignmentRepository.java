package com.slippery.lmsexample.repository;

import com.slippery.lmsexample.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
}
