package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    SplitMenuButton accountSplitMenuButton;
    @FXML
    ChoiceBox<String> categoryChoiceBox;
    @FXML
    TextField searchField;

    public void initialize() {
        categoryChoiceBox.getItems().addAll("Components", "Computers", "Smartphones", "TV and Audio", "Consoles and Videogames", "No Category");
        categoryChoiceBox.setValue("Select Category");
    }

    @FXML
    void goToEditAccountInfo(ActionEvent event) throws IOException {

    }

    @FXML
    void goToPurchaseHistory(ActionEvent event) throws IOException {

    }

    @FXML
    void goToBasket(ActionEvent event) throws IOException {

    }

    @FXML
    void goToLogOut(ActionEvent event) throws IOException {
        Stage stage = (Stage) accountSplitMenuButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/login.fxml"));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(false);
    }

    public void switchWindow(Stage stage, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
