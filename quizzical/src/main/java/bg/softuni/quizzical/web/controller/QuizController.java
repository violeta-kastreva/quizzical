package bg.softuni.quizzical.web.controller;

import bg.softuni.quizzical.model.service.*;
import bg.softuni.quizzical.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class QuizController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SchoolClassService schoolClassService;
    private final QuizService quizService;
    private final QuestionService questionService;
    private final AnswersService answersService;


    public QuizController(UserService userService, ModelMapper modelMapper, SchoolClassService schoolClassService, QuizService quizService, QuestionService questionService, AnswersService answersService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.schoolClassService = schoolClassService;
        this.quizService = quizService;
        this.questionService = questionService;
        this.answersService = answersService;
    }

    private class ListContainer {
        private List<QuestionDTO> questionDTOS;
        public ListContainer() {
            questionDTOS = new ArrayList<>();
        }
        public List<QuestionDTO> getQuestionDTOS() {
            return questionDTOS;
        }
        public void setQuestionDTOS(List<QuestionDTO> questionDTOS) {
            this.questionDTOS = questionDTOS;
        }
    }

    @GetMapping("/createquestion")
    public String createQuestion(Model model, @RequestParam String quizName, @RequestParam String answerCount, @RequestParam String questionsCount){
        if (model.getAttribute("questionCreateBindingModel") == null ||  ((ListContainer)model.getAttribute("questionCreateBindingModel")).getQuestionDTOS().isEmpty()
        ) {
            List<QuestionDTO> questionDTOS = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(questionsCount) ; i++) {
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setText("");
                questionDTO.setQuizName(quizName);
                for (int j = 0; j < Integer.parseInt(answerCount) ; j++) {
                    AnswerDTO answerDTO = new AnswerDTO();
                    answerDTO.setContent("");

                    questionDTO.getAnswers().add(answerDTO);
                }
                questionDTOS.add(questionDTO);
            }
            ListContainer listContainer = new ListContainer();
            listContainer.setQuestionDTOS(questionDTOS);
            model.addAttribute("questionCreateBindingModel",listContainer);

        }
        model.addAttribute("pointsDropdown", this.questionService.loadPoints());

        return "views/teachers/createquestion";
    }

    @PostMapping(value = "/createquestion", consumes = "application/x-www-form-urlencoded")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String createQuestion(@Valid @ModelAttribute @RequestBody ListContainer questionCreateBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("questionCreateBindingModel", questionCreateBindingModel);
            redirectAttributes.addFlashAttribute(String.format("BINDING_RESULT" + "questionCreateBindingModel"), bindingResult);
            return "redirect:/createquestion";
        }

        this.questionService.addQuestions(questionCreateBindingModel.getQuestionDTOS());

        return "redirect:/hometeacher";

    }


    @GetMapping("/createquiz")
    public String createQuiz(Model model, Principal principal){
        if (!model.containsAttribute("quizCreateBindingModel")) {
            model.addAttribute("quizCreateBindingModel", new QuizDTO());
        }
        model.addAttribute("groups", this.schoolClassService.findAllByEmail(principal.getName()));
        model.addAttribute("questionsDropdown", this.questionService.loadQuestions());
        model.addAttribute("answersDropdown", this.questionService.loadAnswers());

        return "views/teachers/createquiz";
    }


    @PostMapping("/createquiz")
    public String createQuizConfirm(@Valid @ModelAttribute("quizCreateBindingModel") QuizDTO quizAddBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("quizCreateBindingModel", quizAddBindingModel);
            redirectAttributes.addFlashAttribute(String.format("BINDING_RESULT" + "quizCreateBindingModel"), bindingResult);
            return "redirect:/createquiz";
        }
        QuizDTO quizDTO = this.modelMapper.map(quizAddBindingModel, QuizDTO.class);
        this.quizService.createQuiz(quizDTO);
        redirectAttributes.addAttribute("answerCount", quizDTO.getAnswerCount());
        redirectAttributes.addAttribute("questionsCount", quizDTO.getQuestionsCount());

        return "redirect:/createquestion?quizName="+quizDTO.getCaption();
    }

    @GetMapping("/createdquizzes")
    public String createdQuizzes(Model model, Principal principal){
        model.addAttribute("quizzes", this.quizService.findAllByEmail(principal.getName()));
        return "views/teachers/createdquizzes";
    }

    @GetMapping("/myquizzes")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public String myQuizzes(Model model, Principal principal){
        model.addAttribute("quizzes", this.quizService.findAllByEmail(principal.getName()));
        return "views/students/myquizzes";
    }

    @GetMapping("/takequiz")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public String takeQuiz(Model model, @RequestParam String quizName){
        if (!model.containsAttribute("takeQuizBindingModel") ) {
            List<QuestionDTO> questionDTOS = this.quizService.getQuizByName(quizName);
            ListContainer listContainer = new ListContainer();
            listContainer.setQuestionDTOS(questionDTOS);
            model.addAttribute("takeQuizBindingModel", listContainer);
        }
        return "views/students/takequiz";
    }

    @PostMapping(value = "/takequiz", consumes = "application/x-www-form-urlencoded")
    public String submitQuiz(@Valid @ModelAttribute @RequestBody ListContainer takeQuizBindingModel,
                             BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "redirect:/homestudent";
        }
        this.quizService.takenQuiz(takeQuizBindingModel.getQuestionDTOS(), principal.getName());
        return "redirect:/homestudent";
    }

    @GetMapping("/myresults")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public String results(Model model, Principal principal){
        if (!model.containsAttribute("quizPointsView") ) {
            List<QuizUserDTO> quizPoints = this.quizService.getScoreByUser(principal.getName());

            model.addAttribute("quizPointsView", quizPoints);
        }
        return "views/students/myresults";
    }

    @GetMapping("/allresults")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String resultsAll(Model model, Principal principal){
        if (!model.containsAttribute("quizPointsView") ) {
            List<QuizUserDTO> quizPoints = this.quizService.getAllScoresByUser(principal.getName());

            model.addAttribute("quizPointsView", quizPoints);
        }
        return "views/teachers/allresults";
    }

    @GetMapping("/teacherprofile")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String profileData(Model model, Principal principal){
        if (!model.containsAttribute("accountInfo") ) {
            UserAccountDTO userAccountDTO = this.quizService.getAccountInfo(principal.getName());
            model.addAttribute("accountInfo", userAccountDTO);
        }
        return "views/teachers/teacherprofile";
    }

    @GetMapping("/studentprofile")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public String profileDataStudent(Model model, Principal principal){
        if (!model.containsAttribute("accountInfo") ) {
            UserAccountDTO userAccountDTO = this.quizService.getAccountInfo(principal.getName());
            model.addAttribute("accountInfo", userAccountDTO);
        }
        return "views/students/studentprofile";
    }


}
