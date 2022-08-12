package bg.softuni.quizzical.web.controller;

import bg.softuni.quizzical.model.entity.Quiz;
import bg.softuni.quizzical.model.service.SchoolClassDTO;
import bg.softuni.quizzical.service.QuizService;
import bg.softuni.quizzical.service.SchoolClassService;
import bg.softuni.quizzical.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class GroupController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SchoolClassService schoolClassService;
    private final QuizService quizService;

    private Quiz currentQuiz;

    public GroupController(UserService userService, ModelMapper modelMapper, SchoolClassService schoolClassService, QuizService quizService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.schoolClassService = schoolClassService;
        this.quizService = quizService;
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

    @GetMapping("/createdgroups")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String createdGroups(Model model, Principal principal){

        model.addAttribute("groups", this.schoolClassService.findAllByEmail(principal.getName()));

        return "views/teachers/createdgroups";
    }


    @GetMapping("/allstudents")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String allStudents(Model model){

        model.addAttribute("students", this.userService.findAllByRole("ROLE_STUDENT"));

        return "views/teachers/allstudents";
    }


    @GetMapping("/mygroups")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public String myGroups(Model model, Principal principal){

        model.addAttribute("groups", this.schoolClassService.findAllByEmail(principal.getName()));

        return "views/students/groups";
    }


    @GetMapping("/allteachers")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public String allTeachers(Model model){

        model.addAttribute("teachers", this.userService.findAllByRole("ROLE_TEACHER"));

        return "views/students/allteachers";
    }



}
