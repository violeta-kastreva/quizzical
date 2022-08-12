package bg.softuni.quizzical.web.controller;


import bg.softuni.quizzical.model.entity.*;
import bg.softuni.quizzical.model.service.QuestionDTO;
import bg.softuni.quizzical.model.service.QuizDTO;
import bg.softuni.quizzical.model.service.SchoolClassDTO;
import bg.softuni.quizzical.model.service.UserDTO;
import bg.softuni.quizzical.repository.QuizRepository;
import bg.softuni.quizzical.repository.RoleRepository;
import bg.softuni.quizzical.repository.SchoolClassRepository;
import bg.softuni.quizzical.repository.UserRepository;
import bg.softuni.quizzical.service.SchoolClassService;
import bg.softuni.quizzical.service.UserService;
import bg.softuni.quizzical.service.impl.QuestionServiceImpl;
import bg.softuni.quizzical.service.impl.QuizServiceImpl;
import bg.softuni.quizzical.service.impl.SchoolClassServiceImpl;
import bg.softuni.quizzical.service.impl.UserServiceImpl;
import org.apache.catalina.Group;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class QuizControllerTest {

    private int VALID_ID;

    private String VALID_USER_ID;

    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_FIRST_NAME = "Violeta";
    private static final String VALID_LAST_NAME = "Kastreva";
    private static final String VALID_EMAIL = "vili@gmail.com";

    private String quizName;
    private String answerCount;
    private String questionCount;

    private static final String VALID_IMAGE_URL = "[random_url]";
    private Role teacher;
    private Role student;
    private Quiz quiz;
    private SchoolClass schoolClass;
    private Question question;
    private User author;

//    @NotBlank(message = "Caption is required")
//    private String caption;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Future(message = "Due date should be in the future")
//    private LocalDate dueDate;
//
//    @NotBlank(message = "Quiz should be assigned to a group")
//    private String schoolClassName;
//
//    private int answerCount;
//
//    private int questionsCount;

    private String VALID_CAPTION = "caption";
    private String VALID_SCHOOL_CLASSNAME = "caption";
    private LocalDate LOCALDATE = LocalDate.of(2023, 1, 8);

    private UserDTO userDTO;
    private SchoolClassDTO schoolClassDTO;
    private QuizDTO quizDTO;
    private QuestionDTO questionDTO;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private SchoolClassRepository schoolClassRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @MockBean
    private QuizServiceImpl quizService;

    @MockBean
    private SchoolClassServiceImpl schoolClassService;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private QuestionServiceImpl questionService;



    @BeforeEach
    public void setUp() {

        teacher = roleRepository.findFirstByAuthority("ROLE_TEACHER").orElse(null);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        modelMapper = new ModelMapper();

        quizName = "NAME";
        answerCount = "3";
        questionCount = "2";

    }



    @Test
    @WithMockUser(username = "violeta@abf.bg", password = "1234", roles = "TEACHER")
    public void viewQuizzes() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);


        this.mockMvc.perform(get("/createquiz")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/teachers/createquiz"));
    }

    @Test
    @WithMockUser(username = "violeta@abf.bg", password = "1234", roles = "STUDENT")
    public void viewQuizzesStudent() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);

        this.mockMvc.perform(get("/myquizzes")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/students/myquizzes"));

    }


    @Test
    @WithMockUser(username = "violeta@abf.bg", password = "1234", roles = "TEACHER")
    public void createQuizShouldThrow() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);

        this.mockMvc.perform(post("/createquiz")
                .param("caption", VALID_CAPTION)
                .param("schoolClassName", VALID_SCHOOL_CLASSNAME)
                .param("dueDate", "11-11-2020")
                .param("answerCount", "2")
                .param("questionCount", "3")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/createquiz"));
    }
}
