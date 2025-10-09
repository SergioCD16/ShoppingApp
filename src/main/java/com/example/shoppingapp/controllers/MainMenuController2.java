package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.Database;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainMenuController2 {
    @FXML
    SplitMenuButton accountSplitMenuButton;
    @FXML
    ChoiceBox<String> categoryChoiceBox;
    @FXML
    TextField searchField;
    @FXML
    Label titleLabel1;
    @FXML
    Label descriptionLabel1;
    @FXML
    Label priceLabel1;
    @FXML
    ImageView productImage1;
    @FXML
    private FlowPane productContainer;

    private User user;
    private IndividualUser indUser;
    private BusinessUser busUser;
    private Address address;
    private CreditCard creditCard;
    private int userID;
    private List<Product> productslist;

    public void initialize() {
        List<Product> products = Database.getAllProducts();

        for (Product product : products) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/product_card.fxml"));
                AnchorPane productCard = loader.load();

                ProductCardController controller = loader.getController();
                controller.setData(product);

                productContainer.getChildren().add(productCard);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void goToEditAccountInfo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/edit_user_info.fxml"));
        Parent root = loader.load();

        EditUserInfoController controller = loader.getController();

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

        Stage stage = (Stage) accountSplitMenuButton.getScene().getWindow();
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(false);
    }

    @FXML
    void goToPurchaseHistory(ActionEvent event) throws IOException {

    }

    @FXML
    void goToLogOut(ActionEvent event) throws IOException {
        Stage stage = (Stage) accountSplitMenuButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/login.fxml"));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(false);
    }

    @FXML
    void goToBasket(ActionEvent event) throws IOException {

    }

    public void setUser(User user, IndividualUser indUser, Address address, CreditCard creditCard, int userID) {
        this.user = user;
        this.indUser = indUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;
    }

    public void setUser(User user, BusinessUser busUser, Address address, CreditCard creditCard, int userID) {
        this.user = user;
        this.busUser = busUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;
    }

    public void setProducts() {
        Product productI;
        for (int i = 0; i < productslist.size(); i++) {
            productI = productslist.get(i);
            titleLabel1.setText(productI.getTitle());
            descriptionLabel1.setText(productI.getDescription());
            priceLabel1.setText(String.valueOf(productI.getPrice()) + " €");
            Image image = new Image(new ByteArrayInputStream(productI.getPicture()));
            productImage1.setImage(image);
            productImage1.setFitWidth(150);
            productImage1.setFitHeight(150);
            productImage1.setPreserveRatio(true);
        }
    }
}
