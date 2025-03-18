package com.slippery.lmsexample.repository;

import com.slippery.lmsexample.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
