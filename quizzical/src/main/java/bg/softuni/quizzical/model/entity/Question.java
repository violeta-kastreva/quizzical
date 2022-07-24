package bg.softuni.quizzical.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question extends BaseEntity{

    @Column(nullable = false)
    private String text;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quizzes_id")
    private Quiz quiz;

    @Column(nullable = false)
    private int points;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

}
