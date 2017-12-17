package me.rizaldijs.telepati.server;

import me.rizaldijs.telepati.common.Endpoint;
import me.rizaldijs.telepati.common.TFQuiz;
import me.rizaldijs.telepati.common.TFQuizAnswer;
import me.rizaldijs.telepati.common.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class TelepatiController {

    @Autowired
    private SimpMessagingTemplate template;
    private TFQuiz quiz;
    @Autowired
    private CachedTFQuizRepository quizRepository;
    private Map<String, Integer> scoreboard = new LinkedHashMap<>();

    @MessageMapping("/join")
    @SendTo("/server/quiz")
    public TFQuiz handleJoin(String username) {
        System.out.println("Welcome, " + username);
        scoreboard.put(username, 0);
        template.convertAndSend(Endpoint.scoreboard, scoreboard);
        return quiz;
    }

    @MessageMapping("/leave")
    public void handleLeave(String username) {
        System.out.println("Goodbye, " + username);
        scoreboard.remove(username);
        template.convertAndSend(Endpoint.scoreboard, scoreboard);
    }

    @MessageMapping("/answer")
    public void handleAnswer(TFQuizAnswer answer) {
        System.out.println(answer.toString());
        String info;
        if (answer.getAnswer().equals(quiz.getAnswer())) {
            info = answer.getUser() + " berhasil menjawab dengan benar!";
            scoreboard.put(answer.getUser(), scoreboard.get(answer.getUser()) + 10);
        }
        else {
            info = "jawaban " + answer.getUser() + " salah";
            scoreboard.put(answer.getUser(), scoreboard.get(answer.getUser()) - 10);
        }
        template.convertAndSend(Endpoint.scoreboard, scoreboard);
        template.convertAndSend(Endpoint.info, new TextMessage("system", info));
    }

    @Scheduled(fixedRate = 10000)
    public void sendQuestion() {
        List<TFQuiz> quizzes = quizRepository.findAll();
        Random rand = new Random();
        quiz = quizzes.get(rand.nextInt(quizzes.size()));
        System.out.println("Quiz: " + quiz.getQuestion());
        template.convertAndSend(Endpoint.quiz, quiz);
    }
}
