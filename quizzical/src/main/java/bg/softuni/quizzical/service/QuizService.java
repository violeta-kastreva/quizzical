package bg.softuni.quizzical.service;

import bg.softuni.quizzical.model.entity.Quiz;
import bg.softuni.quizzical.model.service.QuestionDTO;
import bg.softuni.quizzical.model.service.QuizDTO;
import bg.softuni.quizzical.model.service.QuizUserDTO;
import bg.softuni.quizzical.model.service.UserAccountDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QuizService {
    Quiz createQuiz(QuizDTO quizDTO);

    Set<QuizDTO> findAllByEmail(String name);

    List<QuestionDTO> getQuizByName(String quizName);

    void takenQuiz(List<QuestionDTO> questionDTOS, String name);

    List<QuizUserDTO> getScoreByUser(String name);

    int getTotalPoints(Quiz quiz);

    List<QuizUserDTO> getAllScoresByUser(String name);

    UserAccountDTO getAccountInfo(String name);

    @Transactional
    void deleteOldQuizzes();
}
