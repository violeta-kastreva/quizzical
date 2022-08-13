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
import bg.softuni.quizzical.service.impl.QuestionServiceImpl;
import bg.softuni.quizzical.service.impl.QuizServiceImpl;
import bg.softuni.quizzical.service.impl.SchoolClassServiceImpl;
import bg.softuni.quizzical.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class GroupControllerTest {

    private String quizName;
    private String answerCount;
    private String questionCount;

    private Role teacher;
    private Role student;
    private Quiz quiz;
    private SchoolClass schoolClass;
    private Question question;
    private User author;

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
    @WithMockUser(username = "violeta@abv.bg", password = "1234", roles = "TEACHER")
    public void viewGroups() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);


        this.mockMvc.perform(get("/creategroup")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/teachers/creategroup"));
    }


    @Test
    @WithMockUser(username = "violeta@abv.bg", password = "1234", roles = "STUDENT")
    public void viewGroupsStudent() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);


        this.mockMvc.perform(get("/mygroups")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/students/groups"));
    }

    @Test
    @WithMockUser(username = "violeta@abv.bg", password = "1234", roles = "STUDENT")
    public void viewTeachersAsAStudent() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);


        this.mockMvc.perform(get("/allteachers")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/students/allteachers"));
    }


    @Test
    @WithMockUser(username = "violeta@abv.bg", password = "1234", roles = "TEACHER")
    public void viewStudentsAsATeacher() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);


        this.mockMvc.perform(get("/allstudents")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/teachers/allstudents"));
    }

    @Test
    @WithMockUser(username = "violeta@abv.bg", password = "1234", roles = "TEACHER")
    public void createGroupShouldRedirectToHome() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);

        this.mockMvc.perform(post("/creategroup")
                .param("name", "groupName")
                .param("students", String.valueOf(List.of(new UserDTO("s", "s", "s@s.fe", "1234", "1234", new HashSet<Role>(Set.of(this.roleRepository.findFirstByAuthority("ROLE_STUDENT").get()))))))
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/hometeacher"));
    }

    @Test
    @WithMockUser(username = "violeta@abv.bg", password = "1234", roles = "TEACHER")
    public void createGroupShouldRedirectToGroup() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userDTO);

        this.mockMvc.perform(post("/creategroup")
                .param("name", "")
                .param("students", String.valueOf(List.of(new UserDTO("s", "s", "s@s.fe", "1234", "1234", new HashSet<Role>(Set.of(this.roleRepository.findFirstByAuthority("ROLE_STUDENT").get()))))))
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/creategroup"));
    }


}
