package bg.softuni.quizzical.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question extends BaseEntity{

    @Column(nullable = false)
    private String text;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "quizzes_id")
    private Quiz quizzes;

    @Column
    private int points;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    public Question() {
        this.answers = new HashSet<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Quiz getQuiz() {
        return quizzes;
    }

    public void setQuiz(Quiz quiz) {
        this.quizzes = quiz;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
}
