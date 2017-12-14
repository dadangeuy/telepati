package me.rizaldijs.telepati.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.concurrent.ExecutionException;

public class LoginView {
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;

    @FXML
    public void onClickLoginButton() throws ExecutionException, InterruptedException {
        String username = usernameField.getText().trim();
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    public String getUsername() {
        return usernameField.getText();
    }
}
