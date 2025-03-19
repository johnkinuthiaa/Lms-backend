package com.slippery.lmsexample.service;

import com.slippery.lmsexample.dto.QuizDto;
import com.slippery.lmsexample.models.Quiz;

public interface QuizService {
    QuizDto createNewQuiz(Long adminId,Long lessonId, Quiz quiz);
    QuizDto getQuizById(Long quizId);

}
