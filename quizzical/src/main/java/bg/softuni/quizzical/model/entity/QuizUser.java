package bg.softuni.quizzical.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "quizzes_users_result")
//@IdClass(QuizUserId.class)
public class QuizUser extends BaseEntity{

//    @EmbeddedId
//    private QuizUserId id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(name = "result")
    private Integer result = null;

    public QuizUser() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (o == null || getClass() != o.getClass())
//            return false;
//
//        QuizUser that = (QuizUser) o;
//        return Objects.equals(quizzes_id, that.quizzes_id) &&
//                Objects.equals(users_id, that.users_id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(users_id, quizzes_id);
//    }
}
