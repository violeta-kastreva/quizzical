package bg.softuni.quizzical.service;

import bg.softuni.quizzical.model.entity.Quiz;
import bg.softuni.quizzical.model.service.QuizDTO;

public interface QuizService {
    Quiz createQuiz(QuizDTO quizDTO);
}
