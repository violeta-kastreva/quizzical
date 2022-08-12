package bg.softuni.quizzical.service.impl;

import bg.softuni.quizzical.model.entity.*;
import bg.softuni.quizzical.model.service.*;
import bg.softuni.quizzical.repository.QuizRepository;
import bg.softuni.quizzical.repository.QuizUserRepository;
import bg.softuni.quizzical.repository.SchoolClassRepository;
import bg.softuni.quizzical.repository.UserRepository;
import bg.softuni.quizzical.service.QuizService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    private SchoolClassRepository schoolClassRepository;
    private QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizUserRepository quizUserRepository;

    public QuizServiceImpl(SchoolClassRepository schoolClassRepository, QuizRepository quizRepository, UserRepository userRepository, QuizUserRepository quizUserRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.quizUserRepository = quizUserRepository;
    }

    @Override
    public Quiz createQuiz(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setCaption(quizDTO.getCaption());
        quiz.setDueDate(quizDTO.getDueDate());
        SchoolClass schoolClass = this.schoolClassRepository.findByName(quizDTO.getSchoolClassName()).get();
        schoolClass.getQuizzes().add(quiz);
        quiz.setSchoolClass(schoolClass);
        this.quizRepository.save(quiz);
        return quiz;
    }

    @Override
    public Set<QuizDTO> findAllByEmail(String email) {
        User user = this.userRepository.findFirstByEmail(email).get();
        Set<SchoolClass> allByUser = user.getClasses();
        Set<QuizDTO> allQuizzes = new HashSet<QuizDTO>();
        allByUser.stream().forEach(c->c.getQuizzes().stream().forEach(quiz -> {
            QuizDTO quizDTO = new QuizDTO();
            quizDTO.setCaption(quiz.getCaption());
            quizDTO.setDueDate(quiz.getDueDate());
            quizDTO.setSchoolClassName(quiz.getSchoolClass().getName());
            allQuizzes.add(quizDTO);
        }));
        return allQuizzes;
    }

    @Override
    @Transactional
    public List<QuestionDTO> getQuizByName(String quizName) {
        Quiz quiz = this.quizRepository.findFirstByCaption(quizName).get();
        if(quiz.getQuestions() == null || quiz.getQuestions().isEmpty()){
            return new ArrayList<QuestionDTO>();
        }
        Set<Question> questions = quiz.getQuestions();

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        questions.stream().forEach(q->{
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuizName(quizName);
            questionDTO.setText(q.getText());
            questionDTO.setPoints(q.getPoints());
            q.getAnswers().stream().forEach(a->{
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setContent(a.getContent());
                answerDTO.setIsCorrectAnswer(a.isCorrectAnswer());
                answerDTO.setIsChecked(false);
                questionDTO.getAnswers().add(answerDTO);
            });
            questionDTOS.add(questionDTO);
        });
        return questionDTOS;
    }

    @Override
    @Transactional
    public void takenQuiz(List<QuestionDTO> questionDTOS, String name) {
        Quiz quiz = this.quizRepository.findFirstByCaption(questionDTOS.get(0).getQuizName()).get();
        int score = 0;
        for (QuestionDTO questionDTO: questionDTOS) {
            boolean hasWrongAnswer = false;

            for (AnswerDTO answerDTO: questionDTO.getAnswers()){
                if (answerDTO.getIsChecked()!=answerDTO.getIsCorrectAnswer() ){
                   hasWrongAnswer = true;
                }
            }
            if(!hasWrongAnswer){
                score+=questionDTO.getPoints();
            }
        }

        QuizUser quizUser = new QuizUser();
        quizUser.setQuiz(quiz);
        quizUser.setUser(this.userRepository.findFirstByEmail(name).get());
        quizUser.setResult(score);

        this.quizUserRepository.save(quizUser);



    }

    @Override
    @Transactional
    public List<QuizUserDTO> getScoreByUser(String name) {
        List<QuizUser> quizUsers = this.quizUserRepository.findAllByUserId(this.userRepository.findFirstByEmail(name).get().getId());
        List<QuizUserDTO> resultList= new ArrayList<>();
        quizUsers.forEach(q->{
            QuizUserDTO quizUserDTO = new QuizUserDTO();
            Quiz quiz = q.getQuiz();
            quizUserDTO.setScore(q.getResult());
            quizUserDTO.setQuizName(quiz.getCaption());
            quizUserDTO.setTotalPoints(getTotalPoints(quiz));
            resultList.add(quizUserDTO);
        });
        return resultList;


    }

    @Override
    @Transactional
    public int getTotalPoints(Quiz quiz){
        return quiz.getQuestions().stream().mapToInt(q->q.getPoints()).sum();
    }

    @Override
    @Transactional
    public List<QuizUserDTO> getAllScoresByUser(String name) {
        List<QuizUser> quizUsers = this.quizUserRepository.findAll();
        List<QuizUserDTO> resultList= new ArrayList<>();
        quizUsers.forEach(q->{
            QuizUserDTO quizUserDTO = new QuizUserDTO();
            Quiz quiz = q.getQuiz();
            User user = q.getUser();
            quizUserDTO.setUsername(user.getUsername());
            quizUserDTO.setScore(q.getResult());
            quizUserDTO.setQuizName(quiz.getCaption());
            quizUserDTO.setTotalPoints(getTotalPoints(quiz));
            resultList.add(quizUserDTO);
        });
        return resultList;
    }

    @Override
    @Transactional
    public UserAccountDTO getAccountInfo(String name) {
        User user = this.userRepository.findFirstByEmail(name).get();
        return new UserAccountDTO(user.getEmail(), user.getFirstName(), user.getLastName());
    }
}
