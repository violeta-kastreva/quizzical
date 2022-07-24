package bg.softuni.quizzical.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "quizzes")
public class Quiz extends BaseEntity{

    @Column(nullable = false)
    private String caption;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private int duration;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_classes_id")
    private SchoolClass schoolClass;

    @OneToMany(mappedBy = "quiz")
    private Set<Question> questions;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User student;



}
