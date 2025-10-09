package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ProductCardController {
    @FXML
    private AnchorPane cardRoot;
    @FXML
    private ImageView productImage;
    @FXML
    private Label productTitle;
    @FXML
    private Label productPrice;

    private Product product;

    public void setData(Product product) {
        this.product = product;
        productTitle.setText(product.getTitle());
        productPrice.setText(String.format("%.2f €", product.getPrice()));

        if (product.getPicture() != null) {
            Image image = new Image(new ByteArrayInputStream(product.getPicture()));
            productImage.setImage(image);
        }

        cardRoot.setOnMouseClicked(event -> openProductDetails());
    }

    private void openProductDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/product_details.fxml"));
            Parent root = loader.load();

            // ProductDetailsController controller = loader.getController();
            // controller.setProduct(product);

            Stage stage = (Stage) cardRoot.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}