package bg.softuni.quizzical.repository;

import bg.softuni.quizzical.model.entity.SchoolClass;
import bg.softuni.quizzical.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

    @Modifying
    @Query("update User u set u.classes = :classes where u.email = :email")
    void updateClasses(@Param(value = "email") String email, @Param(value = "classes") Set<SchoolClass> classes);

}