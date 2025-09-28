package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.Database;
import com.example.shoppingapp.utils.FXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AdminManageUsersController {
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Integer> colUserID;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colPhoneNumber;
    @FXML
    private TableColumn<User, String> colType;

    @FXML
    void goToAdminMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/shoppingapp/admin_menu.fxml")));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void initialize() {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        List<User> users = Database.getAllUsers();
        usersTable.getItems().setAll(users);
    }

    @FXML
    private void selectUser(ActionEvent event) throws IOException {
        User userAux = usersTable.getSelectionModel().getSelectedItem();
        if (userAux != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/admin_edit_user.fxml"));
            Parent root = loader.load();

            AdminEditUserController controller = loader.getController();

            int userID = userAux.getUserID();
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

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Produtem");
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            FXUtils.showError("Selection error", "Select a user from the table");
        }
    }

}
