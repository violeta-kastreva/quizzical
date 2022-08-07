package bg.softuni.quizzical.model.service;

import java.util.HashSet;
import java.util.Set;

public class QuestionCollectionDTO {
    Set<QuestionDTO> questions;

    public QuestionCollectionDTO() {
        this.questions = new HashSet<>();
    }

    public Set<QuestionDTO> getQuestion() {
        return questions;
    }

    public void setQuestions(Set<QuestionDTO> questions) {
        this.questions = questions;
    }
}
