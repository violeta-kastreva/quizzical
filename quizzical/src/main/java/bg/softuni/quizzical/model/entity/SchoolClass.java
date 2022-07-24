package bg.softuni.quizzical.model.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "school_classes")
public class SchoolClass extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "classes")
    private Set<User> users;

    //TODO change all to collection as wanted
    @OneToMany(mappedBy = "schoolClass")
    private Set<Quiz> quizzes;

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
