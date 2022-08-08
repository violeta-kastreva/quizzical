package bg.softuni.quizzical.model.service;

import javax.persistence.Column;

public class AnswerDTO {
    private String content;

    private boolean isCorrectAnswer;



    public AnswerDTO() {
    }

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
}
