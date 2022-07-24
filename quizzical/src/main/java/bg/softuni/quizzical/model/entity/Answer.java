package bg.softuni.quizzical.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer extends BaseEntity{
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isCorrectAnswer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "questions_id")
    private Question question;
}
