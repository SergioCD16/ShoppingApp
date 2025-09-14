package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import java.sql.SQLException;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    private PasswordField passwordField;
    @FXML
    private PasswordField cPasswordField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField DNICIFField;
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
    private ChoiceBox<String> chooseUserType;
    @FXML
    private Label DNICIFLabel;

    @FXML
    public void initialize() {
        chooseUserType.getItems().addAll("Individual User", "Business User");
        chooseUserType.setValue("Individual User"); // default option

        chooseUserType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Individual User")) {
                DNICIFLabel.setText("DNI: ");
            } else {
                DNICIFLabel.setText("CIF: ");
            }
        });
    }

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
            String selectedRole = chooseUserType.getValue();

            // problema, si esta vacio da error, pero se comprueba despues
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate expDate = LocalDate.parse(expirationDateField.getText(), formatter);

            boolean checkResult;
            if (selectedRole.equals("Individual User")) {
                checkResult = CheckRegistration.checkCompleteRegistration(true, usernameField.getText(), emailField.getText(), DNICIFField.getText(), phoneField.getText(), passwordField.getText(), cPasswordField.getText(), streetNameField.getText(), numberField.getText(), zipCodeField.getText(), cityField.getText(), creditCardField.getText(), fullNameField.getText(), CVVField.getText(), expDate);
            } else {
                checkResult = CheckRegistration.checkCompleteRegistration(false, usernameField.getText(), emailField.getText(), DNICIFField.getText(), phoneField.getText(), passwordField.getText(), cPasswordField.getText(), streetNameField.getText(), numberField.getText(), zipCodeField.getText(), cityField.getText(), creditCardField.getText(), fullNameField.getText(), CVVField.getText(), expDate);
            }

            if (checkResult) {
                String streetName = Address.capitalizeWords(streetNameField.getText());
                String fullName = Address.capitalizeWords(fullNameField.getText());
                String city = Address.capitalizeWords(cityField.getText());

                CreditCard creditCard = new CreditCard(fullName, creditCardField.getText(), CVVField.getText(), expDate);
                Address address = new Address(streetName, numberField.getText(), zipCodeField.getText(), city);
                User user = new User(usernameField.getText(), emailField.getText(), passwordField.getText(), phoneField.getText());

                if (selectedRole.equals("Individual User")) {
                    IndividualUser indUser = new IndividualUser(user.getName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), DNICIFField.getText());
                    Database.addIndividualUser(user, indUser, address, creditCard);
                } else {
                    BusinessUser busUser = new BusinessUser(user.getName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), DNICIFField.getText());
                    Database.addBusinessUser(user, busUser, address, creditCard);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
