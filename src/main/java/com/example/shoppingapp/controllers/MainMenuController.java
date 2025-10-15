package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainMenuController {
    @FXML
    SplitMenuButton accountSplitMenuButton;
    @FXML
    ChoiceBox<String> categoryChoiceBox;
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
        categoryChoiceBox.getItems().addAll("Components", "Computers", "Smartphones", "TV and Audio", "Consoles and Videogames", "No Category");
        categoryChoiceBox.setValue("Select Category");
        productslist = Database.getAllProducts();

        this.user = UserStore.getInstance().getUser();
        this.indUser = UserStore.getInstance().getIndUser();
        this.busUser = UserStore.getInstance().getBusUser();
        this.address = UserStore.getInstance().getAddress();
        this.creditCard = UserStore.getInstance().getCreditCard();
        this.userID = UserStore.getInstance().getUserID();

        List<Product> products = Database.getAllProducts();

        for (int i = 0; i < products.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/product_card.fxml"));
                AnchorPane productCard = loader.load();

                ProductCardController controller = loader.getController();

                controller.setProduct(products.get(i));

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

        Stage stage = (Stage) accountSplitMenuButton.getScene().getWindow();
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(false);
        stage.setWidth(670);
        stage.setHeight(425);
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
        stage.setWidth(670);
        stage.setHeight(425);
    }

    @FXML
    void goToBasket(ActionEvent event) throws IOException {
        Stage stage = (Stage) accountSplitMenuButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/user_basket.fxml"));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(false);
        stage.setWidth(670);
        stage.setHeight(425);
    }
}
