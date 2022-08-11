package bg.softuni.quizzical.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer extends BaseEntity{
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isCorrectAnswer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "questions_id")
    private Question question;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
