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

import static com.example.shoppingapp.utils.CheckRegistration.checkProductRegistration;

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

    public void switchWindow(Stage stage, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void addPicture() {

    }

    @FXML
    void addProduct() {
        try {
            // hacer parte de picture: annadir picture boton, comprobar imagen y su formato, cambios en la imagen para unificar formato, guardar imagen como string?
            // hacer database.addProduct
            String picture = "";

            boolean checkProduct = checkProductRegistration(titleField.getText(), priceField.getText(), stockField.getText(), descriptionField.getText(), descriptionField.getText());
            if (checkProduct) {
                float price = Float.parseFloat(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                Product product = new Product(titleField.getText(), categoryChoiceBox.getValue(), descriptionField.getText(), picture, price, stock);
                // Database.addProduct(product);
                FXUtils.showInformation("Registration complete", "The product has been registered successfully");
                Stage stage = (Stage) titleField.getScene().getWindow();
                switchWindow(stage, "/com/example/shoppingapp/admin_manage_users.fxml");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
