package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AdminEditProductController {
    @FXML
    private Button goBackButton;
    @FXML
    private Button removeProductButton;
    @FXML
    private Button editProductButton;
    @FXML
    private Button pictureButton;
    @FXML
    private TextField titleField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ChoiceBox categoryChoiceBox;
    @FXML
    private ImageView pictureImage;

    byte[] productImage = null;
    private Product product;
    private int productID;

    @FXML
    void goToAdminMenu(ActionEvent event) throws IOException {
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

    public void setProduct(Product product, int productID) {
        this.product = product;
        this.productID = productID;

        titleField.setText(product.getTitle());
        priceField.setText(Float.toString(product.getPrice()));
        stockField.setText(Integer.toString(product.getStock()));
        descriptionField.setText(product.getDescription());

        categoryChoiceBox.getItems().addAll("Components", "Computers", "Smartphones", "TV and Audio", "Consoles and Videogames", "No Category");
        categoryChoiceBox.setValue(product.getCategory());

        byte[] pictureData = product.getPicture();
        Image image = new Image(new ByteArrayInputStream(pictureData));
        pictureImage.setImage(image);
        pictureImage.setFitWidth(150);
        pictureImage.setFitHeight(150);
        pictureImage.setPreserveRatio(true);
    }

    @FXML
    private void editProduct() throws IOException, SQLException, ClassNotFoundException {

    }

    @FXML
    private void removeProduct() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("This action cannot be undone");
        alert.setContentText("Are you sure you want to delete this product?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            Database.deleteProduct(this.productID);
            FXUtils.showInformation("Product deleted", "The product has been removed successfully");
            Stage stage = (Stage) titleField.getScene().getWindow();
            switchWindow(stage, "/com/example/shoppingapp/admin_manage_products.fxml");
        }
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
}
