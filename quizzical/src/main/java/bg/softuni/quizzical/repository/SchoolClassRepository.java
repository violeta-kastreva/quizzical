package bg.softuni.quizzical.repository;


import bg.softuni.quizzical.model.entity.SchoolClass;
import bg.softuni.quizzical.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

}
