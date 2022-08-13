package bg.softuni.quizzical.web.controller;
import bg.softuni.quizzical.model.service.SchoolClassDTO;
import bg.softuni.quizzical.service.SchoolClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SchoolClassService schoolClassService;

    @BeforeEach
    public void setUp() {
        SchoolClassDTO schoolClassDTO = new SchoolClassDTO();
        schoolClassDTO.setName("TestClass");

        List<String> students = new ArrayList<>() {{
            add("student@student.com");
            add("student2@student.com");
        }};

        schoolClassDTO.setStudents(students);
        schoolClassService.createSchoolClass(schoolClassDTO, "teacher@teacher.com");
    }

    @Test
    @WithMockUser(username = "teacher@teacher", roles = {"TEACHER"})
    public void testCreateQuestionWithAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/createquestion?quizName=quiz&answerCount=2&questionsCount=2")
                .with(csrf()))
                .andExpect(status().isOk());
    }

}