package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Optional;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;

import static com.example.shoppingapp.utils.CheckRegistration.checkProductRegistration;

public class AdminRegisterProductController {
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
    private ImageView pictureImage;
    @ FXML
    private Button pictureButton;
    byte[] productImage = null;

    @FXML
    public void initialize() {
        categoryChoiceBox.getItems().addAll("Components", "Computers", "Smartphones", "TV and Audio", "Consoles and Videogames", "No Category");
        categoryChoiceBox.setValue("No Category"); // default option
    }

    @FXML
    void goToAdminMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/admin_menu.fxml"));
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
    private void addPicture() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);

        Stage stage = (Stage) pictureButton.getScene().getWindow();
        File selectedImageFile = fileChooser.showOpenDialog(stage);

        if (selectedImageFile != null) {
            productImage = Files.readAllBytes(selectedImageFile.toPath());

            Image image = new Image(selectedImageFile.toURI().toString());
            pictureImage.setImage(image);
            pictureImage.setFitWidth(150);
            pictureImage.setFitHeight(150);
            pictureImage.setPreserveRatio(true);
        }
    }


    @FXML
    void addProduct() {
        try {
            boolean checkProduct = checkProductRegistration(titleField.getText(), priceField.getText(), stockField.getText(), descriptionField.getText(), productImage);
            if (checkProduct) {
                if (productImage == null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("No image selected");
                    alert.setHeaderText("No image was chosen");
                    alert.setContentText("Do you want to continue without uploading a picture?");

                    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

                    alert.getButtonTypes().setAll(yesButton, noButton);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == yesButton) {
                        float price = Float.parseFloat(priceField.getText());
                        int stock = Integer.parseInt(stockField.getText());
                        Product product = new Product(titleField.getText(), categoryChoiceBox.getValue(), descriptionField.getText(), productImage, price, stock);
                        Database.addProduct(product);
                        FXUtils.showInformation("Registration complete", "The product has been registered successfully");
                        Stage stage = (Stage) titleField.getScene().getWindow();
                        switchWindow(stage, "/com/example/shoppingapp/admin_menu.fxml");
                    }
                } else {
                    float price = Float.parseFloat(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    Product product = new Product(titleField.getText(), categoryChoiceBox.getValue(), descriptionField.getText(), productImage, price, stock);
                    Database.addProduct(product);
                    FXUtils.showInformation("Registration complete", "The product has been registered successfully");
                    Stage stage = (Stage) titleField.getScene().getWindow();
                    switchWindow(stage, "/com/example/shoppingapp/admin_menu.fxml");
                }
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
