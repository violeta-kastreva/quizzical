package bg.softuni.quizzical.repository;

import bg.softuni.quizzical.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    Optional<Quiz> findFirstByCaption(String caption);
}
