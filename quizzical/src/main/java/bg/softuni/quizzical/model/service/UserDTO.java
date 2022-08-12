package bg.softuni.quizzical.model.service;

import bg.softuni.quizzical.model.entity.Role;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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

    private Set<Role> authorities;

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }



    public UserDTO(@NotBlank(message = "First name is required") String firstName, @NotBlank(message = "Last name is required") String lastName, @NotNull @Email(message = "Email must be valid") String email, @NotNull @Length(min = 3, max = 25, message = "Password must be between 3 and 25 least 3 characters.") String password, @NotNull @Length(min = 3, max = 25, message = "Password must be between 3 and 25  characters.") String confirmPassword, Set<Role> authorities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.authorities = authorities;
    }

    public UserDTO() {
    }
}
