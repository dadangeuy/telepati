package me.rizaldijs.telepati.client.deprecated;

import me.rizaldijs.telepati.common.TFQuiz;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.lang.reflect.Type;

public class QuizHandler extends StompSessionHandlerAdapter {

    private ListenableFutureCallback<TFQuiz> callback;

    public QuizHandler(ListenableFutureCallback<TFQuiz> callback) {
        this.callback = callback;
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return TFQuiz.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        TFQuiz quiz = (TFQuiz) o;
        callback.onSuccess(quiz);
    }

    @Override
    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        callback.onFailure(throwable);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        callback.onFailure(throwable);
    }
}
