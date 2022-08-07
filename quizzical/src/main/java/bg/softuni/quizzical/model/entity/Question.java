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

    @Column
    private int points;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
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
