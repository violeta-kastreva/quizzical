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

    public boolean getIsCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setIsCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }
}
