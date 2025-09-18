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

public class RegisterProductAdminController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    public void initialize() {
        categoryChoiceBox.getItems().addAll("Components", "Computers", "Smartphones", "TV and Audio", "Consoles and Videogames", "No Category");
        categoryChoiceBox.setValue("No Category"); // default option
    }

    @FXML
    void goToProductMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/admin_manage_products.fxml"));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void addPicture() {

    }

    @FXML
    void addProduct() {

    }

//    @FXML
//    void addProduct() {
//        try {
//            // pasar string a float y string a int
//            String picture;
//            Product product = new Product(titleField.getText(), categoryChoiceBox.getValue(), descriptionField.getText(), picture, );
//            boolean checkProduct = checkProductRegistration(product);
//            if (checkProduct) {
//                Database.addProduct(user, indUser, address, creditCard);
//                FXUtils.showInformation("Registration complete", "The user has been registered successfully");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
