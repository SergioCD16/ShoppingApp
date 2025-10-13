package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.*;
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
import java.util.Optional;

public class EditUserInfoController {
    @FXML
    private Button loginButton;
    @FXML
    private Button acceptRegisterButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField cPasswordField;
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
    private Label titleLabel;

    private User user;
    private IndividualUser indUser;
    private BusinessUser busUser;
    private Address address;
    private CreditCard creditCard;
    private int userID;

    @FXML
    public void initialize() {
        this.user = UserStore.getInstance().getUser();
        this.indUser = UserStore.getInstance().getIndUser();
        this.busUser = UserStore.getInstance().getBusUser();
        this.address = UserStore.getInstance().getAddress();
        this.creditCard = UserStore.getInstance().getCreditCard();
        this.userID = UserStore.getInstance().getUserID();

        usernameField.setText(user.getName());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhoneNumber());
        if (user.getType().equals("INDIVIDUAL")) {
            DNICIFField.setText(indUser.getDNI());
        } else {
            DNICIFField.setText(busUser.getCIF());
        }
        streetNameField.setText(address.getStreetName());
        numberField.setText(address.getNumber());
        zipCodeField.setText(address.getZipCode());
        cityField.setText(address.getCity());
        creditCardField.setText(creditCard.getNumber());
        fullNameField.setText(creditCard.getName());
        CVVField.setText(creditCard.getCVV());
        expirationDateField.setText(creditCard.getExpDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/main_menu.fxml"));
        Parent root = loader.load();

        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(true);
    }

    @FXML
    private void editUser() throws IOException, SQLException, ClassNotFoundException {
        boolean indOrBus = false;
        if (user.getType().equals("INDIVIDUAL")) {
            indOrBus = true;
        }
        boolean userCheck = CheckRegistration.checkEditUser(indOrBus, usernameField.getText(), emailField.getText(), DNICIFField.getText(), phoneField.getText());

        boolean passwordCheck = true;
        if (!(passwordField.getText().equals("") && cPasswordField.getText().equals(""))) {
            passwordCheck = CheckRegistration.checkPassword(passwordField.getText(), cPasswordField.getText());
        }

        boolean addressCheck = CheckRegistration.checkAddressRegistration(streetNameField.getText(), numberField.getText(), zipCodeField.getText(), cityField.getText());
        boolean creditCardCheck = CheckRegistration.checkCreditCardRegistration(creditCardField.getText(), fullNameField.getText(), CVVField.getText(), expirationDateField.getText());

        if (userCheck && passwordCheck && addressCheck && creditCardCheck) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Edit");
            alert.setHeaderText("Make sure all information is correct before proceeding");
            alert.setContentText("Are you sure you want to save the changes to this user?");

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == yesButton) {
                String hashPassword = User.hashPassword(passwordField.getText());
                user.setName(usernameField.getText());
                user.setEmail(emailField.getText());
                user.setPhoneNumber(phoneField.getText());
                user.setPassword(hashPassword);
                if (user.getType().equals("INDIVIDUAL")) {
                    indUser.setDNI(DNICIFField.getText());
                } else {
                    busUser.setCIF(DNICIFField.getText());
                }
                user.setUserID(this.userID);

                address.setStreetName(streetNameField.getText());
                address.setNumber(numberField.getText());
                address.setZipCode(zipCodeField.getText());
                address.setCity(cityField.getText());

                creditCard.setNumber(creditCardField.getText());
                creditCard.setName(fullNameField.getText());
                creditCard.setCVV(CVVField.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate expDate = LocalDate.parse(expirationDateField.getText(), formatter);
                creditCard.setExpDate(expDate);

                if (user.getType().equals("INDIVIDUAL")) {
                    Database.updateIndividualUser(user, indUser, address, creditCard);
                } else {
                    Database.updateBusinessUser(user, busUser, address, creditCard);
                }
                FXUtils.showInformation("Changes completed", "Your account has been edited successfully");

                Stage stage = (Stage) fullNameField.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/main_menu.fxml"));
                Parent root = loader.load();

                stage.setTitle("Produtem");
                stage.setScene(new Scene(root));
                stage.show();
                stage.setMaximized(true);
            }
        }
    }
}
