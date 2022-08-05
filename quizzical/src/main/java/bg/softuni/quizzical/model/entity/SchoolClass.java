package bg.softuni.quizzical.model.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "school_classes")
public class SchoolClass extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "classes", fetch = FetchType.EAGER)
    private Set<User> users;

    //TODO change all to collection as wanted
    @OneToMany(mappedBy = "schoolClass", fetch = FetchType.EAGER)
    private Set<Quiz> quizzes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public SchoolClass() {
        this.users = new HashSet<>();
        this.quizzes = new HashSet<>();
    }

//    @OneToMany
//    private List<User> students;

//    @ManyToOne
//    @JoinColumn(name="groups_joined_id", nullable=false)
//    private User student;
//
//    @ManyToOne
//    @JoinColumn(name="creator_id", nullable=false)
//    private User teacher;

}
