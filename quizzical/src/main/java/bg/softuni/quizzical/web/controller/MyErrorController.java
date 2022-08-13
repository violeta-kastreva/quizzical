package bg.softuni.quizzical.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.boot.web.servlet.error.ErrorController;


import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        model.addAttribute("statusCode",statusCode);
        return "/error";
    }

    public String getErrorPath() {
        return "/error";
    }
}
