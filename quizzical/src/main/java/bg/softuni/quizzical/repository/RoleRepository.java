package bg.softuni.quizzical.repository;

import bg.softuni.quizzical.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findFirstByAuthority(String authority);

    Optional<Role> findAllByAuthority(String authority);
}