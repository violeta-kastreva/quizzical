package bg.softuni.quizzical.model.service;

import bg.softuni.quizzical.model.entity.SchoolClass;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class QuizDTO extends BaseDTO{

    @NotBlank(message = "Caption is required")
    private String caption;

    @NotBlank(message = "Due date is required")
    private LocalDate dueDate;

    @NotBlank(message = "Quiz should be assigned to a group")
    private SchoolClass schoolClass;

}
