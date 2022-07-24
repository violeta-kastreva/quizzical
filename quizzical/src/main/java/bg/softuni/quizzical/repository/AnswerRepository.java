package bg.softuni.quizzical.repository;

import bg.softuni.quizzical.model.entity.Answer;
import bg.softuni.quizzical.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findAllByQuestion(Question question);
}
