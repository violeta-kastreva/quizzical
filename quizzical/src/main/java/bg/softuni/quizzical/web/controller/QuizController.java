package bg.softuni.quizzical.web.controller;

import bg.softuni.quizzical.error.FileStorageException;
import bg.softuni.quizzical.model.entity.SchoolClass;
import bg.softuni.quizzical.model.service.QuizDTO;
import bg.softuni.quizzical.model.service.SchoolClassDTO;
import bg.softuni.quizzical.service.SchoolClassService;
import bg.softuni.quizzical.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class QuizController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SchoolClassService schoolClassService;

    public QuizController(UserService userService, ModelMapper modelMapper, SchoolClassService schoolClassService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.schoolClassService = schoolClassService;
    }

    @GetMapping("/createquiz")
    public String createQuiz(Model model, Principal principal){
        if (!model.containsAttribute("quizCreateBindingModel")) {
            model.addAttribute("quizCreateBindingModel", new QuizDTO());
        }
        model.addAttribute("groups", this.schoolClassService.findAllByEmail(principal.getName()));

        return "views/teachers/createquiz";
    }

    @GetMapping("/createquestion")
    public String createQuestion(){
        return "views/teachers/createquestion";
    }

    @GetMapping("/creategroup")
    public String createGroup(Model model){
        if (!model.containsAttribute("groupCreateBindingModel")) {
            model.addAttribute("groupCreateBindingModel", new SchoolClassDTO());
        }
        model.addAttribute("students", this.userService.findAllByRole("ROLE_STUDENT"));

        return "views/teachers/creategroup";
    }

    @PostMapping("/creategroup")
    public String createGroupConfirm(@Valid @ModelAttribute("groupCreateBindingModel") SchoolClassDTO classAddBindingModel,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes, Principal principal) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("groupCreateBindingModel", classAddBindingModel);
            redirectAttributes.addFlashAttribute(String.format("BINDING_RESULT" + "groupCreateBindingModel"), bindingResult);
            return "redirect:/creategroup";
        }

        SchoolClassDTO schoolClass = this.modelMapper.map(classAddBindingModel, SchoolClassDTO.class);

       this.schoolClassService.createSchoolClass(schoolClass, principal.getName());

        return "redirect:/hometeacher";

    }

    @PostMapping("/createquiz")
    public String createQuizConfirm(@Valid @ModelAttribute("groupCreateBindingModel") SchoolClassDTO classAddBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Principal principal) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("groupCreateBindingModel", classAddBindingModel);
            redirectAttributes.addFlashAttribute(String.format("BINDING_RESULT" + "groupCreateBindingModel"), bindingResult);
            return "redirect:/createquiz";
        }

        SchoolClassDTO schoolClass = this.modelMapper.map(classAddBindingModel, SchoolClassDTO.class);

        this.schoolClassService.createSchoolClass(schoolClass, principal.getName());

        return "redirect:/hometeacher";

    }

}
