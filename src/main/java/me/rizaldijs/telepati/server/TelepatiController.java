package me.rizaldijs.telepati.server;

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
    private LinkedHashSet<String> players = new LinkedHashSet<>();

    @MessageMapping("/join")
    @SendTo("/server/play")
    public TFQuiz handleJoin(String username) {
        System.out.println("Welcome, " + username);
        updateNewUser(username);
        return quiz;
    }

    @MessageMapping("/leave")
    public void handleLeave(String username) {
        System.out.println("Goodbye, " + username);
        updateLeavingUser(username);
    }

    @MessageMapping("/answer")
    @SendTo("/server/info")
    public TextMessage handleAnswer(TFQuizAnswer answer) {
        System.out.println(answer.toString());
        String info;
        if (answer.getAnswer().equals(quiz.getAnswer())) info = answer.getUser() + " berhasil menjawab dengan benar!";
        else info = "jawaban " + answer.getUser() + " salah";
        return new TextMessage("System", info);
    }

    public void updateNewUser(String username) {
        players.add(username);
        List<String> listPlayer = new ArrayList<>(players);
        template.convertAndSend("/server/players", listPlayer);
    }

    public void updateLeavingUser(String username) {
        players.remove(username);
        List<String> listPlayer = new ArrayList<>(players);
        template.convertAndSend("/server/players", listPlayer);
    }

    @Scheduled(fixedRate = 10000)
    public void sendQuestion() {
        List<TFQuiz> quizzes = quizRepository.findAll();
        Random rand = new Random();
        quiz = quizzes.get(rand.nextInt(quizzes.size()));
        System.out.println("Quiz: " + quiz.getQuestion());
        template.convertAndSend("/server/play", quiz);
    }
}
