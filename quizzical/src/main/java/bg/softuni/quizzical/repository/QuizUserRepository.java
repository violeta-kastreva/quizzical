package bg.softuni.quizzical.repository;

import bg.softuni.quizzical.model.entity.QuizUser;
import bg.softuni.quizzical.model.entity.Role;
import bg.softuni.quizzical.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizUserRepository extends JpaRepository<QuizUser,Long> {
    List<QuizUser> findAllByUserId(int user_id);

    List<QuizUser> findAllByUser(User user);
}
