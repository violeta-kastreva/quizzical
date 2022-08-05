package bg.softuni.quizzical.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {



    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_TEACHER")) {
             return "redirect:/hometeacher";
        }

        if (request.isUserInRole("ROLE_STUDENT")) {
            return "redirect:/homestudent";
        }

        return "views/users/login";
    }
}
