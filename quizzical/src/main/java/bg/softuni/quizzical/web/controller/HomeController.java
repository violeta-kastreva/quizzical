package bg.softuni.quizzical.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String indexPage(){
        return "views/home/index";
    }

    @GetMapping("/homestudent")
    public String homePageStudent(){
        return "views/students/index";
    }

    @GetMapping("/hometeacher")
    public String homePageTeacher(){
        return "views/teachers/index";
    }

}
