package me.rizaldijs.telepati.client;

import me.rizaldijs.telepati.common.Endpoint;
import me.rizaldijs.telepati.common.TFQuizAnswer;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class TelepatiClient {
    private StompSession session;
    private String username;
    private WebSocketClient client;
    private WebSocketStompClient stompClient;

    public TelepatiClient(
            String url,
            String username,
            StompSessionHandler connectHandler
    ) throws ExecutionException, InterruptedException {
        this.username = username;
        client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        ListenableFuture<StompSession> futureStompSession = stompClient.connect(url + Endpoint.connect, connectHandler);
        session = futureStompSession.get();
    }

    public void subscribe(String source, StompSessionHandler handler) {
        session.subscribe(source, handler);
    }

    public void join() {
        session.send(Endpoint.join, username);
    }

    public void leave() {
        session.send(Endpoint.leave, username);
        session.disconnect();
        stompClient.stop();
    }

    public void answer(Boolean answer) {
        session.send(Endpoint.answer, new TFQuizAnswer(username, answer));
    }

}
