package bg.softuni.quizzical.service;

import bg.softuni.quizzical.model.entity.Quiz;
import bg.softuni.quizzical.model.service.QuizDTO;

import java.util.List;
import java.util.Set;

public interface QuizService {
    Quiz createQuiz(QuizDTO quizDTO);

    Set<QuizDTO> findAllByEmail(String name);
}
