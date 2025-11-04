package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.Product;
import com.example.shoppingapp.classes.Review;
import com.example.shoppingapp.utils.Database;
import com.example.shoppingapp.utils.FXUtils;
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
    @FXML
    private Spinner<Integer> quantitySpinner;

    private Product product;
    private List<Review> reviews;

    private User user;
    private IndividualUser indUser;
    private BusinessUser busUser;
    private Address address;
    private CreditCard creditCard;
    private int userID;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantitySpinner.setValueFactory(valueFactory);
        quantitySpinner.setEditable(false);

        this.user = UserStore.getInstance().getUser();
        this.indUser = UserStore.getInstance().getIndUser();
        this.busUser = UserStore.getInstance().getBusUser();
        this.address = UserStore.getInstance().getAddress();
        this.creditCard = UserStore.getInstance().getCreditCard();
        this.userID = UserStore.getInstance().getUserID();
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/main_menu.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(true);
    }

    @FXML
    public void seeReviews(ActionEvent event) throws IOException {

    }

    @FXML public void purchase(ActionEvent event) throws IOException {
        int quantity = quantitySpinner.getValue();
        if (quantity > product.getStock()) {
            FXUtils.showError("Error in quantity chosen", "Choose a quantity smaller or equal to the current stock");
        } else {
            PurchaseOrder purchaseOrder = Database.getPurchaseOrderByUserID(userID);
            if (purchaseOrder == null) {
                purchaseOrder = new PurchaseOrder(userID);
                Database.addPurchaseOrder(purchaseOrder);
                purchaseOrder = Database.getPurchaseOrderByUserID(userID);
            }
            int orderID = purchaseOrder.getOrderID();

            ItemOrder itemOrder = new ItemOrder(product.getProductID(), quantity, orderID);
            Database.addItemOrder(itemOrder);

            FXUtils.showInformation("Product added successfully", "The product has been added to your basket");
        }
    }

    public void setProduct(Product product) {
        this.product = product;

        titleLabel.setText(product.getTitle());
        descriptionLabel.setText(product.getDescription());
        priceLabel.setText(String.valueOf(product.getPrice()) + "€");
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