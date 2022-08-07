package bg.softuni.quizzical.service;

import bg.softuni.quizzical.model.service.AnswerDTO;

import java.util.List;
import java.util.Set;

public interface AnswersService {
    AnswerDTO addAnswersToQuestion(Set<AnswerDTO> answerDTOSet, String questionId);

}
