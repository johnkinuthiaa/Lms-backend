package com.slippery.lmsexample.service.impl;

import com.slippery.lmsexample.dto.QuizDto;
import com.slippery.lmsexample.models.Question;
import com.slippery.lmsexample.models.Quiz;
import com.slippery.lmsexample.repository.QuizRepository;
import com.slippery.lmsexample.service.LessonService;
import com.slippery.lmsexample.service.QuizService;
import com.slippery.lmsexample.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class QuizServiceImplementation implements QuizService {
    private final QuizRepository repository;
    private final LessonService lessonService;
    private final UsersService usersService;

    public QuizServiceImplementation(QuizRepository repository, LessonService lessonService, UsersService usersService) {
        this.repository = repository;
        this.lessonService = lessonService;
        this.usersService = usersService;
    }

    @Override
    public QuizDto createNewQuiz(Long adminId,Long lessonId, Quiz quiz) {
        QuizDto response=new QuizDto();
        var existingLesson =lessonService.findLessonById(lessonId);

        if(usersService.findUserAccountById(adminId).getStatusCode() !=200){
            response.setMessage("User not found");
            response.setStatusCode(404);
            return response;
        }
        if(existingLesson.getStatusCode() !=200){
            response.setMessage("lesson not found");
            response.setStatusCode(404);
            return response;
        }
        var existingAdmin =usersService.findUserAccountById(lessonId).getUser();
        if(!Objects.equals(existingAdmin.getId(), existingLesson.getLesson().getCourseModule().getTutor().getId())){
            response.setMessage("The provided tutor cannot edit the content");
            response.setStatusCode(403);
            return response;
        }
        if(existingLesson.getLesson().getQuiz() !=null){
            response.setMessage("The lesson already has a valid quiz! please update the existing one");
            response.setStatusCode(400);
            return response;
        }
        quiz.setLesson(existingLesson.getLesson());
        for(Question question:quiz.getQuestions()){
            question.setQuiz(quiz);
        }
        repository.save(quiz);
        response.setMessage("New quiz created for "+existingLesson.getLesson().getTitle());
        response.setStatusCode(201);
        return response;
    }

    @Override
    public QuizDto getQuizById(Long quizId) {
        QuizDto response=new QuizDto();
        var existingQuiz =repository.findById(quizId);
        if(existingQuiz.isEmpty()){
            response.setMessage("Quiz with id"+quizId+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        var questions =existingQuiz.get().getQuestions();
        for(Question question:questions){
            question.setCorrectAnswer(null);
        }
        existingQuiz.get().setQuestions(questions);
        response.setQuiz(existingQuiz.get());
        response.setStatusCode(200);
        response.setMessage("Quiz with id "+quizId);
        return response;
    }
}
