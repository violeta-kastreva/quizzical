package bg.softuni.quizzical.service.impl;

import bg.softuni.quizzical.error.InvalidEmailException;
import bg.softuni.quizzical.error.UserAlreadyExistException;
import bg.softuni.quizzical.error.UserNotFoundException;
import bg.softuni.quizzical.model.entity.Role;
import bg.softuni.quizzical.model.entity.User;
import bg.softuni.quizzical.model.service.RoleDTO;
import bg.softuni.quizzical.model.service.UserDTO;
import bg.softuni.quizzical.model.service.UserRegistrationDTO;
import bg.softuni.quizzical.repository.UserRepository;
import bg.softuni.quizzical.service.RoleService;
import bg.softuni.quizzical.service.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
    private final UserRepository userRepository;
    private final RoleService roleService;

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public UserDTO registerNewUserAccount(UserRegistrationDTO userServiceModel) throws UserAlreadyExistException, RoleNotFoundException {
//        Converter<String, Set<Role>> mapRoles = new Converter<String, Set<Role>>() {
//            public Set<Role> convert(MappingContext<String, Set<Role>> context) {
//                return context.getSource() == null ? null : Collections.singleton(this.);
//            }
//        };
//        modelMapper.addConverter(mapRoles);
//
        //UserDTO userDTO = new UserDTO(userServiceModel.getFirstName(), userServiceModel.getLastName(), userServiceModel.getEmail(), userServiceModel.getPassword(), userServiceModel.getConfirmPassword(), userServiceModel.getAuthority());
       // UserDTO userDTO = this.modelMapper.map(userServiceModel, UserDTO.class);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(userServiceModel.getFirstName());
        userDTO.setLastName(userServiceModel.getLastName());
        userDTO.setEmail(userServiceModel.getEmail());
        userDTO.setPassword(userServiceModel.getPassword());
        userDTO.setConfirmPassword(userServiceModel.getConfirmPassword());
        userDTO.setAuthorities(Collections.singleton(this.roleService.findAuthorityByName(userServiceModel.getAuthority())));
//        long count = this.userRepository.count();
//        if (count == 0) {
//            authorities.add(this.roleService.findAuthorityByName("ROLE_TEACHER"));
//
//        } else {
//            authorities.add(this.roleService.findAuthorityByName("ROLE_STUDENT"));
//        }
//        userDTO.setAuthorities(authorities);


        return registerNewUser(userDTO);
    }

    @Override
    public UserDTO createNewAdminAccount(UserDTO adminUser) throws UserAlreadyExistException, RoleNotFoundException {

        Set<Role> authorities = new HashSet<>();
        authorities.add(this.roleService.findAuthorityByName("ROLE_ADMIN"));
        adminUser.setAuthorities(authorities);
        return registerNewUser(adminUser);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));

        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {

        User user = this.userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new InvalidEmailException("User with that email address does't exist !"));

        return this.modelMapper.map(user, UserDTO.class);

    }

    @Override
    public UserDTO findByName(String username) {
        User user = this.userRepository.findFirstByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with given username does not exist! "));

        return this.modelMapper.map(user, UserDTO.class);

    }


    private UserDTO registerNewUser(UserDTO userServiceModel) {
        throwExceptionIfUserExist(userServiceModel.getEmail());

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));


        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserDTO.class);
    }

    private void throwExceptionIfUserExist(String email) {

        User userWithEmail = this.userRepository.findFirstByEmail(email).orElse(null);

        if (userWithEmail != null) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            + email);
        }
    }

    private boolean emailExist(String email) {
        return this.userRepository.findFirstByEmail(email).orElse(null) != null;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findFirstByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid user"));
    }

    @Override
    public boolean hasUserSpecifiedRole(Collection<GrantedAuthority> authorities, String role) {
        GrantedAuthority authority = () -> role;
        return authorities.contains(authority);
    }

    @Override
    public List<UserDTO> findAllByRole(String role) {
        return this.userRepository.findAll().stream()
                .filter(e->{
                   return !(e.getAuthorities().stream().filter(r -> r.getAuthority().equals(role)).count() == 0);
                })
                .map(u->this.modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
    }
}
