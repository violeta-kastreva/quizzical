package bg.softuni.quizzical.repository;

import bg.softuni.quizzical.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    Optional<Quiz> findFirstByCaption(String caption);

    @Modifying
    @Transactional
    @Query("delete from Quiz q where q.dueDate < :dueDate")
    void deleteQuizzesByDueDateBefore(@Param("dueDate") LocalDate dueDate);
}
