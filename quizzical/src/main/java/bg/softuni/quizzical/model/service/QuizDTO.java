package bg.softuni.quizzical.model.service;

import bg.softuni.quizzical.model.entity.SchoolClass;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

public class QuizDTO extends BaseDTO{

    @NotBlank(message = "Caption is required")
    private String caption;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Due date should be in the future")
    private LocalDate dueDate;

    @NotBlank(message = "Quiz should be assigned to a group")
    private String schoolClassName;

    private int answerCount;

    private int questionsCount;



    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
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

    public String getSchoolClassName() {
        return schoolClassName;
    }

    public void setSchoolClassName(String schoolClassName) {
        this.schoolClassName = schoolClassName;
    }

}
