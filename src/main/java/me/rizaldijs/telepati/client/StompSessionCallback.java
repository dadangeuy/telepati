package me.rizaldijs.telepati.client;

import me.rizaldijs.telepati.common.TFQuiz;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.lang.reflect.Type;

public class StompSessionCallback extends StompSessionHandlerAdapter {

    private Type type;
    private ListenableFutureCallback callback;

    public StompSessionCallback(Type type, ListenableFutureCallback callback) {
        this.type = type;
        this.callback = callback;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        super.afterConnected(session, connectedHeaders);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return type;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        if (payload != null) {
            callback.onSuccess(payload);
        }
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        callback.onFailure(exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        callback.onFailure(exception);
    }
}
