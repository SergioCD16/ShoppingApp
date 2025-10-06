package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.Database;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainMenuController {
    @FXML
    SplitMenuButton accountSplitMenuButton;
    @FXML
    ChoiceBox<String> categoryChoiceBox;
    @FXML
    TextField searchField;

    private User user;
    private IndividualUser indUser;
    private BusinessUser busUser;
    private Address address;
    private CreditCard creditCard;
    private int userID;

    public void initialize() {
        categoryChoiceBox.getItems().addAll("Components", "Computers", "Smartphones", "TV and Audio", "Consoles and Videogames", "No Category");
        categoryChoiceBox.setValue("Select Category");
    }

    @FXML
    void goToEditAccountInfo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/edit_user_info.fxml"));
        Parent root = loader.load();

        EditUserInfoController controller = loader.getController();

        User user = Database.getUserByID(userID);
        Address address = Database.getAddressesByUserID(userID);
        CreditCard creditCard = Database.getCreditCardsByUserID(userID);
        if (user.getType().equals("INDIVIDUAL")) {
            IndividualUser indUser = Database.getIndividualUserByID(userID, user);
            controller.setUser(user, indUser, address, creditCard, userID);
        } else {
            BusinessUser busUser = Database.getBusinessUserByID(userID, user);
            controller.setUser(user, busUser, address, creditCard, userID);
        }

        Stage stage = (Stage) accountSplitMenuButton.getScene().getWindow();
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(false);
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

    public void setUser(User user, IndividualUser indUser, Address address, CreditCard creditCard, int userID) {
        this.user = user;
        this.indUser = indUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;
    }

    public void setUser(User user, BusinessUser busUser, Address address, CreditCard creditCard, int userID) {
        this.user = user;
        this.busUser = busUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;
    }
}
