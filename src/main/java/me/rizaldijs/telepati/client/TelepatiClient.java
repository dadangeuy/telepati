package me.rizaldijs.telepati.client;

import lombok.Setter;
import me.rizaldijs.telepati.common.TFQuizAnswer;
import me.rizaldijs.telepati.common.TextMessage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class TelepatiClient {
    public String url = "ws://localhost:8000/connect";
    private StompSession session;
    private String username;
    private WebSocketClient client;
    private WebSocketStompClient stompClient;

    public TelepatiClient(
            String username,
            StompSessionHandler connectHandler
    ) throws ExecutionException, InterruptedException {
        this.username = username;
        client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        ListenableFuture<StompSession> futureStompSession = stompClient.connect(url, connectHandler);
        session = futureStompSession.get();
    }

    public void subscribeQuiz(StompSessionHandler quizHandler) {
        session.subscribe("/server/play", quizHandler);
    }

    public void subscribePlayers(StompSessionHandler playersHandler) {
        session.subscribe("/server/players", playersHandler);
    }

    public void subscribeInfo(StompSessionHandler infoHandler) {
        session.subscribe("/server/info", infoHandler);
    }

    public void join() {
        session.send("/client/join", username);
    }

    public void leave() {
        session.send("/client/leave", username);
        session.disconnect();
        stompClient.stop();
    }

    public void answer(Boolean answer) {
        session.send("/client/answer", new TFQuizAnswer(username, answer));
    }

}
