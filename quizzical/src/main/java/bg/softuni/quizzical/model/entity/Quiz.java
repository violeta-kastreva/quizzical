package bg.softuni.quizzical.model.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "quizzes")
public class Quiz extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String caption;

    @Column(nullable = false)
    private LocalDate dueDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "school_classes_id")
    private SchoolClass schoolClass;

    @OneToMany(mappedBy = "quizzes")
    private Set<Question> questions;

    @Column
    private int score;

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL
    )
    private List<QuizUser> quizzesUsersResult = new ArrayList<>();

//    public List<QuizUser> getQuizzesUsersResult() {
//        return quizzesUsersResult;
//    }
//
//    public void setQuizzesUsersResult(List<QuizUser> quizzesUsersResult) {
//        this.quizzesUsersResult = quizzesUsersResult;
//    }

    public Quiz() {
        this.questions = new HashSet<>();
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
