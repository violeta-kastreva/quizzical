package bg.softuni.quizzical.service;

import bg.softuni.quizzical.error.InvalidEmailException;
import bg.softuni.quizzical.error.UserAlreadyExistException;
import bg.softuni.quizzical.model.entity.Role;
import bg.softuni.quizzical.model.entity.User;
import bg.softuni.quizzical.model.service.RoleDTO;
import bg.softuni.quizzical.model.service.UserDTO;
import bg.softuni.quizzical.model.service.UserRegistrationDTO;
import bg.softuni.quizzical.repository.UserRepository;
import bg.softuni.quizzical.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String VALID_COURSE_ID = "validId";
    private static final String VALID_NEW_ID = "validId";
    private static final String VALID_TITLE = "Java Master Class";
    private static final String VALID_SHORT_DESCRIPTION = "Curabitur cursus elementum nibh quis dignissim. Mauris tristique, tortor vel auctor elementum, lorem urna sed.";
    private static final String VALID_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nisi lectus, elementum in accumsan nec, porttitor vitae lacus. Nunc tempor sodales sem vitae porttitor. Nullam id iaculis sapien. Nunc non porttitor sapien, sed elementum sem. Suspendisse elementum hendrerit neque, vitae luctus ex interdum sit amet. Morbi eu congue arcu, sed fringilla dolor. Donec pharetra sem et turpis consectetur quis.";
    private static final double VALID_PASS_PERCENTAGE = 100.0;
    private static final int VALID_DURATION_WEEKS = 10;
    private static final boolean VALID_STATUS = false;
    private static final int VALID_COURSE_RATING = 10;

    private static final String VALID_AUTHOR_ID = "validUserId";
    private static final String VALID_AUTHOR_USERNAME = "rusevrado";
    private static final String VALID_TOPIC_ID = "topicId";
    private static final String VALID_TOPIC_NAME = "Java";

    private static final int VALID_ID = 123;

    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_NEW_PASSWORD = "12345";
    private static final String VALID_FIRST_NAME = "Violeta";
    private static final String VALID_LAST_NAME = "Kastreva";
    private static final String VALID_EMAIL = "violeta@gmail.com";
    private static final String INVALID_EMAIL = "invalid@gmail.com";
    private static final String VALID_NEW_EMAIL = "radorusevcrypto_new@gmail.com";


    Set<Role> authorities = new HashSet<>();

    private Set<User> testRepository;

    private User user;
    private UserDTO testUserDTO;


    private UserRegistrationDTO userRegDTO;

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleService roleService;

    @Mock
    ModelMapper modelMapper;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Before
    public void init() throws RoleNotFoundException {
        ModelMapper actualMapper = new ModelMapper();
        BCryptPasswordEncoder actualEncoder = new BCryptPasswordEncoder();

        testRepository = new HashSet<>();

        when(userRepository.saveAndFlush(isA(User.class)))
                .thenAnswer(invocationOnMock -> {
                    testRepository.add((User) invocationOnMock.getArguments()[0]);
                    return user;
                });

        when(modelMapper.map(any(UserDTO.class), eq(User.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], User.class));

        when(modelMapper.map(any(User.class), eq(UserDTO.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], UserDTO.class));



        userRegDTO = new UserRegistrationDTO();
        userRegDTO.setEmail(VALID_EMAIL);
        userRegDTO.setFirstName(VALID_FIRST_NAME);
        userRegDTO.setLastName(VALID_LAST_NAME);
        userRegDTO.setPassword(VALID_PASSWORD);
        userRegDTO.setConfirmPassword(VALID_PASSWORD);
        userRegDTO.setAuthority("ROLE_STUDENT");

        user = new User();

        user.setId(VALID_ID);
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        user.setAuthorities(authorities);



    }

    @Test
    public void registerConfirm() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userRegDTO);
        UserDTO userDTO = this.userService.registerNewUserAccount(userRegDTO);
        assertEquals(userDTO.getEmail(), VALID_EMAIL);

    }


    @Test(expected = UserAlreadyExistException.class)
    public void registerNewUser_shouldThrowException_WhenEmailAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {
        //Arrange
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));
        //Act //Assert
        userService.registerNewUserAccount(userRegDTO);
    }
}