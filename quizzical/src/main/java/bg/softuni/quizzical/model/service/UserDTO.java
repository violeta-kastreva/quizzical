package bg.softuni.quizzical.model.service;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull
    @Email(message = "Email must be valid")
    private String email;

    @NotNull
    @Length(min = 3, max = 25, message = "Password must be between 3 and 25 least 3 characters.")
    private String password;

    @NotNull
    @Length(min = 3, max = 25,  message = "Password must be between 3 and 25  characters.")
    private String confirmPassword;
}
