package bg.softuni.quizzical.service.impl;

import bg.softuni.quizzical.model.entity.Quiz;
import bg.softuni.quizzical.model.entity.SchoolClass;
import bg.softuni.quizzical.model.entity.User;
import bg.softuni.quizzical.model.service.QuizDTO;
import bg.softuni.quizzical.repository.QuizRepository;
import bg.softuni.quizzical.repository.SchoolClassRepository;
import bg.softuni.quizzical.repository.UserRepository;
import bg.softuni.quizzical.service.QuizService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuizServiceImpl implements QuizService {
    private SchoolClassRepository schoolClassRepository;
    private QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizServiceImpl(SchoolClassRepository schoolClassRepository, QuizRepository quizRepository, UserRepository userRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
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
}
