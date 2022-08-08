package bg.softuni.quizzical.service.impl;

import bg.softuni.quizzical.model.entity.Question;
import bg.softuni.quizzical.model.entity.Quiz;
import bg.softuni.quizzical.model.service.QuestionDTO;
import bg.softuni.quizzical.repository.QuestionRepository;
import bg.softuni.quizzical.repository.QuizRepository;
import bg.softuni.quizzical.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }


    @Override
    public Question addQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setText(questionDTO.getText());
        question.setPoints(questionDTO.getPoints());

        Quiz quiz = quizRepository.findFirstByCaption(questionDTO.getQuizName()).get();
        question.setQuiz(quiz);
        quiz.getQuestions().add(question);
        this.questionRepository.save(question);
        return question;
    }

    @Override
    public List<Integer> loadAnswers() {
        return List.of(2, 3, 4, 5, 6);
    }

    @Override
    public List<Integer> loadPoints() {
        return List.of(1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Override
    public List<Integer> loadQuestions() {
        return List.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
    }

    @Override
    public String getQuizName(String questionId) {
        return this.questionRepository.findById(Long.parseLong(questionId)).get().getQuiz().getCaption();
    }
}