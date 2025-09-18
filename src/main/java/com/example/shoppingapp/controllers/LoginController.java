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

                    String fxmlPath;
                    if (user instanceof IndividualUser || user instanceof BusinessUser) {
                        fxmlPath = "/com/example/shoppingapp/main_page.fxml";
                    } else {
                        fxmlPath = "/com/example/shoppingapp/admin_menu.fxml";
                    }

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                    stage.setScene(new Scene(root));
                    stage.show();

                } else {
                    FXUtils.showError("Login Error", "Incorrect email or password");
                }
            }
        }
    }

    public User login(String email, String password) {
        try {
            String sql = "SELECT u.UserID, u.Name, u.Email, u.Password, u.PhoneNumber, u.Type, " +
                    "i.DNI, b.CIF " + "FROM User u " + "LEFT JOIN IndividualUser i ON u.UserID = i.UserID " +
                    "LEFT JOIN BusinessUser b ON u.UserID = b.UserID " + "WHERE u.Email=? AND u.Password=?";

            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String type = rs.getString("Type");

                        if (type.equals("ADMIN")) {
                            return new User(
                                    rs.getString("Name"),
                                    rs.getString("Email"),
                                    rs.getString("Password"),
                                    rs.getString("PhoneNumber"),
                                    rs.getInt("UserID")
                            );
                        } else if (type.equals("INDIVIDUAL")) {
                            return new IndividualUser(
                                    rs.getString("Name"),
                                    rs.getString("Email"),
                                    rs.getString("Password"),
                                    rs.getString("PhoneNumber"),
                                    rs.getInt("UserID"),
                                    rs.getString("DNI"));
                        } else {
                            return new BusinessUser(
                                    rs.getString("Name"),
                                    rs.getString("Email"),
                                    rs.getString("Password"),
                                    rs.getString("PhoneNumber"),
                                    rs.getInt("UserID"),
                                    rs.getString("CIF"));
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
