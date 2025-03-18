package com.slippery.lmsexample.repository;

import com.slippery.lmsexample.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
}
