package bg.softuni.quizzical.repository;

import bg.softuni.quizzical.model.entity.QuizUser;
import bg.softuni.quizzical.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizUserRepository extends JpaRepository<QuizUser,Long> {
    Optional<QuizUser> findAllByUserId(int user_id);
}
