package bg.softuni.quizzical.model.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails{

    public User() {
        this.classes = new HashSet<>();
    }

    public User(String firstName, String lastName, @Email String email, @Length(min = 3, message = "Password must be at least 3 characters.") String password, Set<Role> authorities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.classes = new HashSet<>();
    }

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, updatable = true)
    @Email
    private String email;

    @Column(nullable = false, updatable = true)
    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String password;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_classes",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "school_classes_id")
    )
    private Set<SchoolClass> classes;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private Set<Role> authorities;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<QuizUser> usersQuizzesResults = new ArrayList<>();

//    public List<QuizUser> getUsersQuizzesResults() {
//        return usersQuizzesResults;
//    }
//
//    public void setUsersQuizzesResults(List<QuizUser> usersQuizzesResults) {
//        this.usersQuizzesResults = usersQuizzesResults;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<SchoolClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<SchoolClass> classes) {
        this.classes = classes;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }


}
