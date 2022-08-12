package bg.softuni.quizzical.web.controller;

import bg.softuni.quizzical.error.UserAlreadyExistException;
import bg.softuni.quizzical.model.service.UserDTO;
import bg.softuni.quizzical.model.service.UserRegistrationDTO;
import bg.softuni.quizzical.repository.RoleRepository;
import bg.softuni.quizzical.repository.UserRepository;
import bg.softuni.quizzical.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserControllerTest {

    private String VALID_USER_ID;
    private static final String VALID_USERNAME = "violeta@abv.bg";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_FIRST_NAME = "Violeta";
    private static final String VALID_LAST_NAME = "Kastreva";
    private static final String VALID_EMAIL = "violeta@abv.bg";

    private UserDTO userDTO;
    private UserRegistrationDTO userRegistrationDTO;
    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @MockBean
    private UserServiceImpl userService;

    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        modelMapper = new ModelMapper();

    }



    @Test
    @WithMockUser(username = "vili@abv.bg", password = "1234", roles = "STUDENT")
    public void login() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);


        this.mockMvc.perform(get("/users/login")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/default"));
    }

    @Test
    public void loginError() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);


        this.mockMvc.perform(get("/users/login")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/users/login"));
    }

    @Test
    public void loginShouldReturnLoginPageWhenUserIsAnonymous() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);

        this.mockMvc.perform(get("/users/login")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/users/login"));


    }

    @Test
    public void register() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);

        this.mockMvc.perform(get("/users/register")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/users/register"));

    }

    @Test
    @WithMockUser(username = "vili@abv.bg", password = "1234", roles = "TEACHER")
    public void registerShouldRedirectToHome() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);

        this.mockMvc.perform(get("/users/register")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/home"));

    }

    @Test
    public void registerConfirm() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);

        this.mockMvc.perform(post("/users/register")
                .param("firstName", VALID_FIRST_NAME)
                .param("lastName", VALID_LAST_NAME)
                .param("email", VALID_EMAIL)
                .param("password", VALID_PASSWORD)
                .param("confirmPassword", VALID_PASSWORD)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/login"));
    }

    @Test
    public void registerConfirmShouldRedirectToRegisterPage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);


        this.mockMvc.perform(post("/users/register")
                .param("firstName", VALID_FIRST_NAME)
                .param("lastName", VALID_LAST_NAME)
                .param("email", VALID_EMAIL)
                .param("password", "13")
                .param("confirmPassword", VALID_PASSWORD)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/register"));
    }

    @Test
    public void registerConfirmShouldRedirectToRegisterPageAndThrowUserAlreadyExistException() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userRegistrationDTO);

        when(userService.registerNewUserAccount(userRegistrationDTO)).thenThrow(UserAlreadyExistException.class);

        this.mockMvc.perform(post("/users/register")
                .param("username", VALID_USERNAME)
                .param("firstName", VALID_FIRST_NAME)
                .param("lastName", VALID_LAST_NAME)
                .param("email", VALID_EMAIL)
                .param("password", "23")
                .param("confirmPassword", VALID_PASSWORD)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/register"));
    }



}
