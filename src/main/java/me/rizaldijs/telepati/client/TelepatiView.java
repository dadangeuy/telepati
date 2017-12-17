package me.rizaldijs.telepati.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class TelepatiView implements Initializable {
    @FXML
    private TextArea quizTextArea;
    @FXML
    private TextField infoTextField;
    @FXML
    private ListView playerListView;
    @FXML
    private ListView scoreListView;
    @FXML
    private Button benarButton;
    @FXML
    private Button salahButton;

    private ObservableList<String> players;
    private ObservableList<Integer> scores;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        players = FXCollections.observableArrayList();
        scores = FXCollections.observableArrayList();
        playerListView.setItems(players);
        scoreListView.setItems(scores);
    }

    public void updateQuiz(String quiz) {
        quizTextArea.setText(quiz);
        disableAnswerButton(false);
    }

    public void updateScoreboards(Map<String, Integer> scoreboard) {
        Platform.runLater(() -> this.players.setAll(scoreboard.keySet()));
        Platform.runLater(() -> this.scores.setAll(scoreboard.values()));
    }

    public void updateInfo(String info) {
        infoTextField.setText(info);
    }

    public void updatePlayers(List<String> players) {
        Platform.setImplicitExit(true);
        Platform.runLater(() -> this.players.setAll(players));
    }

    public void disableAnswerButton(Boolean value) {
        benarButton.setDisable(value);
        salahButton.setDisable(value);
    }

    public void onClickBenar() {
        TelepatiClientGui.client.answer(true);
        disableAnswerButton(true);
    }

    public void onClickSalah() {
        TelepatiClientGui.client.answer(false);
        disableAnswerButton(true);
    }
}

