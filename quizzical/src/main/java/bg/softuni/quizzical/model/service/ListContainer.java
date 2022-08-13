package bg.softuni.quizzical.model.service;

import java.util.ArrayList;
import java.util.List;

public class ListContainer {
    private List<QuestionDTO> questionDTOS;
    public ListContainer() {
        questionDTOS = new ArrayList<>();
    }
    public List<QuestionDTO> getQuestionDTOS() {
        return questionDTOS;
    }
    public void setQuestionDTOS(List<QuestionDTO> questionDTOS) {
        this.questionDTOS = questionDTOS;
    }
}
