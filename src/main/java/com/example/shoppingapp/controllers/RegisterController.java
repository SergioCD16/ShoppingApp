package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;

public class RegisterController {
    @FXML
    private Button loginButton;
    @FXML
    private Button acceptRegisterButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField DNIField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField streetNameField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField creditCardField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField expirationDateField;
    @FXML
    private TextField CVVField;

    @FXML
    void goToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/login.fxml"));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void registerUser() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date expDate = formatter.parse(expirationDateField.getText());

            CreditCard creditCard = new CreditCard(fullNameField.getText(), creditCardField.getText(), CVVField.getText(), expDate);
            Address address = new Address(streetNameField.getText(), numberField.getText(), zipCodeField.getText(), cityField.getText());
            User user = new User(usernameField.getText(), emailField.getText(), DNIField.getText(), phoneField.getText());
            Database.addUser(user, address, creditCard);

            System.out.println("Registro completado");

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
