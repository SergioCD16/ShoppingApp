package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
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
    private Label titleLabel;
    @FXML
    private Label priceLabel;

    private User user;
    private IndividualUser indUser;
    private BusinessUser busUser;
    private Address address;
    private CreditCard creditCard;
    private int userID;
    private Product product;

    public void setData(Product product, User user, IndividualUser indUser, BusinessUser busUser, Address address, CreditCard creditCard, int userID) {
        this.product = product;
        this.user = user;
        this.indUser = indUser;
        this.busUser = busUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;

        titleLabel.setText(product.getTitle());
        priceLabel.setText(String.format("%.2f €", product.getPrice()));

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

            ProductDetailsController controller = loader.getController();
            controller.setData(product, user, indUser, busUser, address, creditCard, userID);

            Stage stage = (Stage) cardRoot.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setMaximized(false);
            stage.setWidth(700);
            stage.setHeight(440);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}