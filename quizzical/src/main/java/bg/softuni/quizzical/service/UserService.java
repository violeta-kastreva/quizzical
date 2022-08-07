package bg.softuni.quizzical.service;

import bg.softuni.quizzical.error.InvalidEmailException;
import bg.softuni.quizzical.error.UserAlreadyExistException;
import bg.softuni.quizzical.model.service.SchoolClassDTO;
import bg.softuni.quizzical.model.service.UserDTO;
import bg.softuni.quizzical.model.service.UserRegistrationDTO;
import org.springframework.security.core.GrantedAuthority;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.List;

public interface UserService {
    UserDTO registerNewUserAccount(UserRegistrationDTO map) throws UserAlreadyExistException, RoleNotFoundException;

    UserDTO findByEmail(String email) throws InvalidEmailException;

    UserDTO findByName(String username);

    UserDTO findById(Long id);

    UserDTO createNewAdminAccount(UserDTO map) throws UserAlreadyExistException, RoleNotFoundException;

    boolean hasUserSpecifiedRole(Collection<GrantedAuthority> authorities, String role);

    List<UserDTO> findAllByRole(String role);

    void seedUsersInDb() throws RoleNotFoundException;




}
