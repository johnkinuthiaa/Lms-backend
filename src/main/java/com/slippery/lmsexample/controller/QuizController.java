package com.slippery.lmsexample.controller;

import com.slippery.lmsexample.dto.QuizDto;
import com.slippery.lmsexample.models.Quiz;
import com.slippery.lmsexample.service.QuizService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
@CrossOrigin
public class QuizController {
    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }
    @PostMapping("/create-quiz")
    public ResponseEntity<QuizDto> createNewQuiz(@RequestParam Long adminId,@RequestParam Long lessonId,@RequestBody Quiz quiz){
        var createdQuiz =service.createNewQuiz(adminId, lessonId, quiz);
        return ResponseEntity.status(HttpStatusCode.valueOf(createdQuiz.getStatusCode())).body(createdQuiz);
    }
    @GetMapping("/{quizId}/get")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long quizId){
        var quiz =service.getQuizById(quizId);
        return ResponseEntity.status(HttpStatusCode.valueOf(quiz.getStatusCode())).body(quiz);
    }
}
