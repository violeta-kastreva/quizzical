package bg.softuni.quizzical.service;

import bg.softuni.quizzical.model.entity.Question;
import bg.softuni.quizzical.model.service.QuestionDTO;
import bg.softuni.quizzical.web.controller.QuizController;

import java.util.List;

public interface QuestionService {
    Question addQuestion(QuestionDTO questionDTO);

    List<Integer> loadAnswers();

    List<Integer> loadPoints();
    List<Integer> loadQuestions();

    String getQuizName(String questionId);

    void addQuestions(List<QuestionDTO> questionDTOList);
}
