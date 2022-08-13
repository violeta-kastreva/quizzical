package bg.softuni.quizzical.service.impl;

import bg.softuni.quizzical.model.entity.Answer;
import bg.softuni.quizzical.model.entity.Question;
import bg.softuni.quizzical.model.entity.Quiz;
import bg.softuni.quizzical.model.service.AnswerDTO;
import bg.softuni.quizzical.model.service.ListContainer;
import bg.softuni.quizzical.model.service.QuestionDTO;
import bg.softuni.quizzical.repository.AnswerRepository;
import bg.softuni.quizzical.repository.QuestionRepository;
import bg.softuni.quizzical.repository.QuizRepository;
import bg.softuni.quizzical.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuizRepository quizRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.answerRepository = answerRepository;
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

    @Override
    @Transactional
    public void addQuestions(List<QuestionDTO> questionDTOList) {
        Quiz quiz = quizRepository.findFirstByCaption(questionDTOList.get(0).getQuizName()).get();

        for (QuestionDTO questionDTO: questionDTOList) {
            Question question = new Question();
            question.setText(questionDTO.getText());
            question.setPoints(questionDTO.getPoints());

            question.setQuiz(quiz);
            quiz.getQuestions().add(question);
            this.questionRepository.save(question);

            for (AnswerDTO answerDTO: questionDTO.getAnswers()) {
                Answer answer = new Answer();
                answer.setCorrectAnswer(answerDTO.getIsCorrectAnswer());
                answer.setContent(answerDTO.getContent());

                answer.setQuestion(question);
                question.getAnswers().add(answer);
                this.answerRepository.save(answer);
            }

        }
    }

    @Override
    public ListContainer createQuestions(String quizName, String questionsCount, String answerCount) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(questionsCount); i++) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setText("");
            questionDTO.setQuizName(quizName);
            for (int j = 0; j < Integer.parseInt(answerCount); j++) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setContent("");

                questionDTO.getAnswers().add(answerDTO);
            }
            questionDTOS.add(questionDTO);
        }
        ListContainer listContainer = new ListContainer();
        listContainer.setQuestionDTOS(questionDTOS);

        return listContainer;
    }

}
