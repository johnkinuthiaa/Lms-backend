package com.slippery.lmsexample.repository;

import com.slippery.lmsexample.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
