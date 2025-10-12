package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.Product;
import com.example.shoppingapp.classes.Review;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import com.example.shoppingapp.classes.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ProductDetailsController {
    @FXML
    private ImageView productImage;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private Label reviewsLabel;
    @FXML
    private Label categoryLabel;

    private Product product;
    private List<Review> reviews;

    private User user;
    private IndividualUser indUser;
    private BusinessUser busUser;
    private Address address;
    private CreditCard creditCard;
    private int userID;

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/main_menu.fxml"));
        Parent root = loader.load();

        MainMenuController controller = loader.getController();
        controller.setUser(user, indUser, busUser, address, creditCard, userID);

        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(true);
    }

    @FXML
    public void seeReviews(ActionEvent event) throws IOException {

    }

    @FXML
    public void purchase(ActionEvent event) throws IOException {

    }

    public void setData(Product product, User user, IndividualUser indUser, BusinessUser busUser, Address address, CreditCard creditCard, int userID) {
        this.product = product;
        this.user = user;
        this.indUser = indUser;
        this.busUser = busUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;

        titleLabel.setText(product.getTitle());
        descriptionLabel.setText(product.getDescription());
        priceLabel.setText(String.valueOf(product.getPrice()) + " €");
        stockLabel.setText("Stock: " + String.valueOf(product.getStock()));
        categoryLabel.setText(product.getCategory());

        if (product.getPicture() != null) {
            Image image = new Image(new ByteArrayInputStream(product.getPicture()));
            productImage.setImage(image);
        }

        productImage.setFitWidth(250);
        productImage.setFitHeight(250);
        productImage.setPreserveRatio(true);
    }
}