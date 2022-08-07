package bg.softuni.quizzical.service.impl;

import bg.softuni.quizzical.model.entity.Quiz;
import bg.softuni.quizzical.model.entity.SchoolClass;
import bg.softuni.quizzical.model.service.QuizDTO;
import bg.softuni.quizzical.repository.QuizRepository;
import bg.softuni.quizzical.repository.SchoolClassRepository;
import bg.softuni.quizzical.service.QuizService;
import bg.softuni.quizzical.service.SchoolClassService;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    private SchoolClassRepository schoolClassRepository;
    private QuizRepository quizRepository;

    public QuizServiceImpl(SchoolClassRepository schoolClassRepository, QuizRepository quizRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.quizRepository = quizRepository;
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
}
