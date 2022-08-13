package bg.softuni.quizzical.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class HomeControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        modelMapper = new ModelMapper();
    }

    @Test
    public void loadIndexPage() throws Exception {
        MockHttpSession session = new MockHttpSession();

        this.mockMvc.perform(get("/")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/home/index"));

    }

    @Test
    @WithMockUser(username = "violeta@abv.bg", password = "1234", roles = "TEACHER")
    public void loadTeacherPage() throws Exception {
        MockHttpSession session = new MockHttpSession();

        this.mockMvc.perform(get("/hometeacher")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/teachers/index"));

    }

    @Test
    @WithMockUser(username = "violeta@abv.bg", password = "1234", roles = "STUDENT")
    public void loadStudentPage() throws Exception {
        MockHttpSession session = new MockHttpSession();

        this.mockMvc.perform(get("/homestudent")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("views/students/index"));

    }
}
