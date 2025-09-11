package com.example.shoppingapp;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;

public class LoginController {
    @FXML
    private Button registerButton;

    @FXML
    void goToRegister(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/register.fxml"));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
