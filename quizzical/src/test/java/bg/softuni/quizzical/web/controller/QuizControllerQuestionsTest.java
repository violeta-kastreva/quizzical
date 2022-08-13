package bg.softuni.quizzical.web.controller;
import bg.softuni.quizzical.model.service.QuizDTO;
import bg.softuni.quizzical.model.service.SchoolClassDTO;
import bg.softuni.quizzical.service.QuizService;
import bg.softuni.quizzical.service.SchoolClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class QuizControllerQuestionsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SchoolClassService schoolClassService;


    @Autowired
    private QuizService quizService;

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

        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setCaption("TestQuiz");
        quizDTO.setDueDate(LocalDate.now().plusDays(1));
        quizDTO.setQuestionsCount(2);
        quizDTO.setAnswerCount(2);
        quizDTO.setSchoolClassName("TestClass");

        quizService.createQuiz(quizDTO);
    }

    @Test
    @WithMockUser(username = "teacher@teacher.com", roles = {"TEACHER"})
    public void testCreateQuestionWithAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/createquestion?quizName=quiz&answerCount=2&questionsCount=2")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "teacher@teacher", roles = {"TEACHER"})
    public void testCreateQuestionPostWithAuth() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(
                "/createquestion")
                .with(csrf())
                .contentType("application/x-www-form-urlencoded")
                .param("quizName", "TestQuiz")
                .param("questionDTOS[0].text", "q1")
                .param("questionDTOS[0].quizName", "TestQuiz")
                .param("questionDTOS[0].points", "1")
                .param("questionDTOS[0].answers[0].isCorrectAnswer", "true")
                .param("_questionDTOS[0].answers[0].isCorrectAnswer", "on")
                .param("questionDTOS[0].answers[0].content", "a1")
                .param("_questionDTOS[0].answers[1].isCorrectAnswer", "on")
                .param("questionDTOS[0].answers[1].content", "a2")

                .param("questionDTOS[1].text", "q2")
                .param("questionDTOS[1].quizName", "TestQuiz")
                .param("questionDTOS[1].points", "1")
                .param("questionDTOS[1].answers[0].isCorrectAnswer", "true")
                .param("_questionDTOS[1].answers[0].isCorrectAnswer", "on")
                .param("questionDTOS[1].answers[0].content", "a3")
                .param("_questionDTOS[1].answers[1].isCorrectAnswer", "on")
                .param("questionDTOS[1].answers[1].content", "a4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hometeacher?favicon=%2Fimg%2Flogo.png"));
    }

    @Test
    @WithMockUser(username = "student@student.com", roles = {"STUDENT"})
    public void testTakeQuizGetWithAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/takequiz?quizName=TestQuiz")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "student@student.com", roles = {"STUDENT"})
    public void testTakeQuizWithAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                "/takequiz")
                .with(csrf())
                .contentType("application/x-www-form-urlencoded")
                .param("quizName", "TestQuiz")
                .param("questionDTOS[0].text", "q1")
                .param("questionDTOS[0].quizName", "TestQuiz")
                .param("questionDTOS[0].points", "1")
                .param("questionDTOS[0].answers[0].isCorrectAnswer", "true")
                .param("_questionDTOS[0].answers[0].isCorrectAnswer", "on")
                .param("questionDTOS[0].answers[0].content", "a1")
                .param("_questionDTOS[0].answers[1].isCorrectAnswer", "on")
                .param("questionDTOS[0].answers[1].content", "a2")

                .param("questionDTOS[1].text", "q2")
                .param("questionDTOS[1].quizName", "TestQuiz")
                .param("questionDTOS[1].points", "1")
                .param("questionDTOS[1].answers[0].isCorrectAnswer", "true")
                .param("_questionDTOS[1].answers[0].isCorrectAnswer", "on")
                .param("questionDTOS[1].answers[0].content", "a3")
                .param("_questionDTOS[1].answers[1].isCorrectAnswer", "on")
                .param("questionDTOS[1].answers[1].content", "a4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/homestudent?favicon=%2Fimg%2Flogo.png"));
    }

}