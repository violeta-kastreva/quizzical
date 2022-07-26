package bg.softuni.quizzical.service;

import bg.softuni.quizzical.error.InvalidEmailException;
import bg.softuni.quizzical.error.UserAlreadyExistException;
import bg.softuni.quizzical.model.service.UserDTO;

import javax.management.relation.RoleNotFoundException;

public interface UserService {
    UserDTO registerNewUserAccount(UserDTO map) throws UserAlreadyExistException, RoleNotFoundException;

    UserDTO findByEmail(String email) throws InvalidEmailException;

    UserDTO findByName(String username);

    UserDTO findById(Long id);

    UserDTO createNewAdminAccount(UserDTO map) throws UserAlreadyExistException, RoleNotFoundException;

}
