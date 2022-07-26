package bg.softuni.quizzical.web.controller;

import bg.softuni.quizzical.error.UserAlreadyExistException;
import bg.softuni.quizzical.model.service.UserDTO;
import bg.softuni.quizzical.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model,
                        HttpServletRequest httpServletRequest) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            return "redirect:/home";
        }

        if (error != null) {
            String exceptionMessage = getErrorMessage(httpServletRequest, "SPRING_SECURITY_LAST_EXCEPTION");
            model.addAttribute("exceptionMessage", exceptionMessage);
        }


        return "users/login";
    }

    private String getErrorMessage(HttpServletRequest httpServletRequest, String key) {

        Exception exception =
                (Exception) httpServletRequest.getSession().getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        }
        return error;
    }


    @GetMapping("/register")
    public String register(Model model) {

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            return "redirect:/home";
        }
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserDTO());
        }
        return "/users/register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserDTO userRegisterBindingModel
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(String.format("userRegisterBindingModel"), bindingResult);
            return "redirect:/users/register";
        }
        try {
            System.out.println();
            userService.registerNewUserAccount(this.modelMapper.map(userRegisterBindingModel, UserDTO.class));
        } catch (UserAlreadyExistException | RoleNotFoundException e) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("exceptionMessage", e.getMessage());
            return "redirect:/users/register";
        }

        return "redirect:/users/login";
    }

}
