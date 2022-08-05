package bg.softuni.quizzical.model.service;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class SchoolClassDTO extends BaseDTO{
    @NotBlank(message = "Name is required")
    private String name;

    private List<String> students;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }
}
