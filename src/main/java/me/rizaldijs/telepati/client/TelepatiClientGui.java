package me.rizaldijs.telepati.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import me.rizaldijs.telepati.common.TFQuiz;
import me.rizaldijs.telepati.common.TextMessage;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

public class TelepatiClientGui extends Application {
    public static TelepatiClient client;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // configure login view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
        Scene loginScene = new Scene((Parent) loader.load());
        Stage loginStage = new Stage();
        loginStage.setScene(loginScene);
        LoginView loginView = loader.getController();
        loginStage.showAndWait();
        String username = loginView.getUsername();
        if (username == null || username.isEmpty()) {
            stop();
            return;
        }

        // configure app view
        loader = new FXMLLoader(getClass().getResource("/TelepatiView.fxml"));
        Scene home = new Scene((Parent) loader.load());
        stage.setScene(home);
        stage.setTitle("Tebak Benar atau Salah (" + username + ")");
        TelepatiView telepatiView = loader.getController();

        // configure callback
        ListenableFutureCallback<TFQuiz> updateQuiz = new ListenableFutureCallback<TFQuiz>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("quiz callback");
                telepatiView.updateInfo(throwable.getMessage());
            }

            @Override
            public void onSuccess(TFQuiz tfQuiz) {
                System.out.println("quiz callback");
                telepatiView.updateQuiz(tfQuiz.getQuestion());
            }
        };
        ListenableFutureCallback<List> updatePlayers = new ListenableFutureCallback<List>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("players callback");
                telepatiView.updateInfo(throwable.getMessage());
            }

            @Override
            public void onSuccess(List list) {
                System.out.println("players callback");
                telepatiView.updatePlayers(list);
            }
        };
        ListenableFutureCallback<TextMessage> infoCallback = new ListenableFutureCallback<TextMessage>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Info Callback");
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(TextMessage textMessage) {
                System.out.println("Info Callback");
                telepatiView.updateInfo(textMessage.getContent());
            }
        };

        client = new TelepatiClient(
                username,
                new StompSessionCallback(TFQuiz.class, updateQuiz)
        );
        client.subscribeQuiz(new StompSessionCallback(TFQuiz.class, updateQuiz));
        client.subscribePlayers(new StompSessionCallback(List.class, updatePlayers));
        client.subscribeInfo(new StompSessionCallback(TextMessage.class, infoCallback));

        client.join();

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        client.leave();
        super.stop();
    }
}
