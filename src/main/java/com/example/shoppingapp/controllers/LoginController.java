package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.Database;
import com.example.shoppingapp.utils.FXUtils;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;

public class LoginController {
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    void goToRegister(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/register.fxml"));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void loginData(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (email.equals("")) {
            FXUtils.showError("Login Error", "Email field is blank");
        } else {
            if  (password.equals("")) {
                FXUtils.showError("Login Error", "Password field is blank");
            } else {
                User user = login(email, password);

                if (user != null) {
                    FXUtils.showInformation("Login Success", "Welcome back to Produtem");
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    String fxmlPath;
                    if (user instanceof IndividualUser || user instanceof BusinessUser) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/main_menu.fxml"));
                        Parent root = loader.load();

                        int userID = user.getUserID();
                        user = Database.getUserByID(userID);
                        Address address = Database.getAddressesByUserID(userID);
                        CreditCard creditCard = Database.getCreditCardsByUserID(userID);
                        if (user.getType().equals("INDIVIDUAL")) {
                            IndividualUser indUser = Database.getIndividualUserByID(userID, user);
                            UserStore.getInstance().setUser(user, indUser, address, creditCard, userID);
                        } else {
                            BusinessUser busUser = Database.getBusinessUserByID(userID, user);
                            UserStore.getInstance().setUser(user, busUser, address, creditCard, userID);
                        }

                        stage.setScene(new Scene(root));
                        stage.show();
                        stage.setMaximized(true);

                    } else {
                        fxmlPath = "/com/example/shoppingapp/admin_menu.fxml";
                        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                        stage.setScene(new Scene(root));
                        stage.show();
                    }

                } else {
                    FXUtils.showError("Login Error", "Incorrect email or password");
                }
            }
        }
    }

    public User login(String email, String plainPassword) {
        try {
            String sql = "SELECT u.UserID, u.Name, u.Email, u.Password, u.PhoneNumber, u.Type, " +
                    "i.DNI, b.CIF " + "FROM User u " + "LEFT JOIN IndividualUser i ON u.UserID = i.UserID " +
                    "LEFT JOIN BusinessUser b ON u.UserID = b.UserID " + "WHERE u.Email=?";

            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);

                try (ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {
                        String type = rs.getString("Type");
                        boolean checkPassword = User.checkPassword(plainPassword, rs.getString("Password"));

                        if (checkPassword){
                            if (type.equals("ADMIN")) {
                                return new User(
                                        rs.getString("Name"),
                                        rs.getString("Email"),
                                        rs.getString("Password"),
                                        rs.getString("PhoneNumber"),
                                        rs.getString("Type"),
                                        rs.getInt("UserID")
                                );
                            } else if (type.equals("INDIVIDUAL")) {
                                return new IndividualUser(
                                        rs.getString("Name"),
                                        rs.getString("Email"),
                                        rs.getString("Password"),
                                        rs.getString("PhoneNumber"),
                                        rs.getInt("UserID"),
                                        rs.getString("DNI"),
                                        rs.getString("Type"));
                            } else {
                                return new BusinessUser(
                                        rs.getString("Name"),
                                        rs.getString("Email"),
                                        rs.getString("Password"),
                                        rs.getString("PhoneNumber"),
                                        rs.getInt("UserID"),
                                        rs.getString("CIF"),
                                        rs.getString("Type"));
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
