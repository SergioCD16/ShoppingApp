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

public class AdminEditUserController {
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
    void goToAdminMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/admin_manage_users.fxml"));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchWindow(Stage stage, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setUser(User user, IndividualUser indUser, Address address, CreditCard creditCard, int userID) {
        DNICIFLabel.setText("DNI: ");
        titleLabel.setText("Edit Individual User");
        this.user = user;
        this.indUser = indUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;

        usernameField.setText(user.getName());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhoneNumber());
        DNICIFField.setText(indUser.getDNI());
        streetNameField.setText(address.getStreetName());
        numberField.setText(address.getNumber());
        zipCodeField.setText(address.getZipCode());
        cityField.setText(address.getCity());
        creditCardField.setText(creditCard.getNumber());
        fullNameField.setText(creditCard.getName());
        CVVField.setText(creditCard.getCVV());
        expirationDateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    public void setUser(User user, BusinessUser busUser, Address address, CreditCard creditCard, int userID) {
        DNICIFLabel.setText("CIF: ");
        titleLabel.setText("Edit Business User");
        this.user = user;
        this.busUser = busUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;

        usernameField.setText(user.getName());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhoneNumber());
        DNICIFField.setText(busUser.getCIF());
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
    private void editUser() throws IOException, SQLException, ClassNotFoundException {
        boolean indOrBus = false;
        if (user.getType().equals("INDIVIDUAL")) {
            indOrBus = true;
        }
        boolean userCheck = CheckRegistration.checkEditUser(indOrBus, usernameField.getText(), emailField.getText(), DNICIFField.getText(), phoneField.getText());
        boolean addressCheck = CheckRegistration.checkAddressRegistration(streetNameField.getText(), numberField.getText(), zipCodeField.getText(), cityField.getText());
        boolean creditCardCheck = CheckRegistration.checkCreditCardRegistration(creditCardField.getText(), fullNameField.getText(), CVVField.getText(), expirationDateField.getText());

        if (userCheck && addressCheck && creditCardCheck) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Edit");
            alert.setHeaderText("Make sure all information is correct before proceeding");
            alert.setContentText("Are you sure you want to save the changes to this user?");

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == yesButton) {
                user.setName(usernameField.getText());
                user.setEmail(emailField.getText());
                user.setPhoneNumber(phoneField.getText());
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
                FXUtils.showInformation("Changes completed", "The user has been edited successfully");
                Stage stage = (Stage) fullNameField.getScene().getWindow();
                switchWindow(stage, "/com/example/shoppingapp/admin_manage_users.fxml");
            }
        }
    }

    @FXML
    private void removeUser() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("This action cannot be undone");
        alert.setContentText("Are you sure you want to delete this user?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            Database.deleteUser(this.userID);
            FXUtils.showInformation("User deleted", "The user has been removed successfully");
            Stage stage = (Stage) fullNameField.getScene().getWindow();
            switchWindow(stage, "/com/example/shoppingapp/admin_manage_users.fxml");
        }
    }
}
