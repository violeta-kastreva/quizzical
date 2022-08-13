package bg.softuni.quizzical.scheduled;


import bg.softuni.quizzical.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledOldQuizDeletion {
    private final QuizService quizService;
    private final static Logger LOGGER= LoggerFactory.getLogger(ScheduledOldQuizDeletion.class);

    public ScheduledOldQuizDeletion(QuizService quizService) {
        this.quizService = quizService;
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void deleteOldQuizzes(){
        LOGGER.info("Deleting old quizzes");
        this.quizService.deleteOldQuizzes();
    }
}
