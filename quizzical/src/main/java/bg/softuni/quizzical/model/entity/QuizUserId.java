package bg.softuni.quizzical.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class QuizUserId implements Serializable {
    @Column(name = "user_id")
    private int user;

    @Column(name = "quiz_id")
    private int quiz;

    public QuizUserId() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        QuizUserId that = (QuizUserId) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(quiz, that.quiz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, quiz);
    }


}
