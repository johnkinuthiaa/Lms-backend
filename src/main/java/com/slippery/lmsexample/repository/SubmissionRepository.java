package com.slippery.lmsexample.repository;

import com.slippery.lmsexample.models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
}
