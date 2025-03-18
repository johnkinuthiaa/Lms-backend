package com.slippery.lmsexample.service;

import com.slippery.lmsexample.dto.LessonDto;
import com.slippery.lmsexample.models.Lesson;

public interface LessonService {
    LessonDto createNewLesson(Long tutorId, Long courseId, Long moduleId, Lesson lessonDetails);
}
