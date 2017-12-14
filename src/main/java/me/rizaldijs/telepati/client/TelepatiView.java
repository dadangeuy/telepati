package me.rizaldijs.telepati.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelepatiView implements Initializable {
    @FXML
    private TextArea quizTextArea;
    @FXML
    private TextField infoTextField;
    @FXML
    private ListView playerListView;
    @FXML
    private Button benarButton;
    @FXML
    private Button salahButton;

    private ObservableList<String> playerList;

    public void updateQuiz(String quiz) {
        quizTextArea.setText(quiz);
        disableAnswerButton(false);
    }

    public void updateInfo(String info) {
        infoTextField.setText(info);
    }

    public void updatePlayers(List<String> players) {
        Platform.setImplicitExit(true);
        Platform.runLater(() -> playerList.setAll(players));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerList = FXCollections.observableArrayList();
        playerListView.setItems(playerList);
    }
}
