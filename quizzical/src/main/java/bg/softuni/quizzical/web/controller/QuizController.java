package bg.softuni.quizzical.web.controller;

import bg.softuni.quizzical.model.service.AnswerDTO;
import bg.softuni.quizzical.model.service.QuestionCollectionDTO;
import bg.softuni.quizzical.model.service.QuestionDTO;
import bg.softuni.quizzical.model.service.QuizDTO;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  /*  @GetMapping("/addanswers/{questionId}")
    public String createAnswers(Model model, @RequestParam String questionId, @RequestParam String answerCount){
        if (!model.containsAttribute("answersCreateBindingModel")) {
            Set<AnswerDTO> answers = new HashSet<>();
            for (int i = 0; i < Integer.parseInt(answerCount); i++) {
                answers.add(new AnswerDTO(questionId));
            }
            model.addAttribute("answersCreateBindingModel", answers);
        }
        return "views/teachers/addanswers/"+questionId;
    }

    @PostMapping("/addanswers/{questionId}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String createAnswers(@Valid @ModelAttribute("answersCreateBindingModel") Set<AnswerDTO> answersCreateBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, @PathVariable String questionId) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("answersCreateBindingModel", answersCreateBindingModel);
            redirectAttributes.addFlashAttribute(String.format("BINDING_RESULT" + "answersCreateBindingModel"), bindingResult);
            return "redirect:/createanswers";
        }

        this.answersService.addAnswersToQuestion(answersCreateBindingModel, questionId);
        String quizName = this.questionService.getQuizName(questionId);

        return "redirect:/createquestion/"+ questionId;

    }

    */



    @GetMapping("/createquestion")
    public String createQuestion(Model model, @RequestParam String quizName, @RequestParam String answerCount, @RequestParam String questionsCount){
        if (!model.containsAttribute("questionCreateBindingModel")) {
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
            model.addAttribute("questionCreateBindingModel", questionDTOS);

        }
        model.addAttribute("pointsDropdown", this.questionService.loadPoints());

        return "views/teachers/createquestion";
    }

    @PostMapping("/createquestion")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String createQuestion(@RequestParam("questionCreateBindingModel") List<QuestionDTO> questionCreateBindingModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        //questionCreateBindingModel.setQuizName(quizName);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("questionCreateBindingModel", questionCreateBindingModel);
            redirectAttributes.addFlashAttribute(String.format("BINDING_RESULT" + "questionCreateBindingModel"), bindingResult);
            return "redirect:/createquestion";
        }
        //questionCreateBindingModel.setQuizName(quizName);

        //int questionId = this.questionService.addQuestion(questionCreateBindingModel).getId();

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
        //redirectAttributes.addAttribute("quizName", quizDTO.getCaption());
        redirectAttributes.addAttribute("answerCount", quizDTO.getAnswerCount());
        redirectAttributes.addAttribute("questionsCount", quizDTO.getQuestionsCount());
        //redirectAttributes.addAttribute("quizName", quizDTO.getCaption());

        return "redirect:/createquestion?quizName="+quizDTO.getCaption();

    }

}
