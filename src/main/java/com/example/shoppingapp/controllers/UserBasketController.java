package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;
import com.example.shoppingapp.utils.Database;
import com.example.shoppingapp.utils.FXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBasketController {
    @FXML
    private TableView<BasketItem> productsTable;
    @FXML
    private TableColumn<Product, Integer> colProduct;
    @FXML
    private TableColumn<Product, String> colQuantity;

    private int userID;
    private Product product;

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/main_menu.fxml"));
        Parent root = loader.load();

        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(true);
    }

    @FXML
    void completePurchase(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Purchase");
        alert.setContentText("Do you want to complete the purchase?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            PurchaseOrder purchaseOrder = Database.getPurchaseOrderByUserID(userID);
            List<ItemOrder> itemOrders = Database.getAllItemOrderByOrderID(purchaseOrder.getOrderID());

            for (ItemOrder itemOrder : itemOrders) {
                int quantity = itemOrder.getQuantity();

                int productID = itemOrder.getProductID();
                product = Database.getProductByID(productID);
                int stock = product.getStock();

                int newStock = stock - quantity;
                product.setStock(newStock);
                Database.updateProduct(product);
            }

            Database.updatePurchaseOrder(purchaseOrder.getOrderID());
            FXUtils.showInformation("Purchase Complete", "The Purchase was completed successfully");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/main_menu.fxml"));
            Parent root = loader.load();

            stage.setTitle("Produtem");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setMaximized(true);
        }
    }

    @FXML
    public void initialize() {
        this.userID = UserStore.getInstance().getUserID();

        colProduct.setCellValueFactory(new PropertyValueFactory<>("title"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        PurchaseOrder purchaseOrder = Database.getPurchaseOrderByUserID(userID);
        if (purchaseOrder != null) {
            List<ItemOrder> itemOrders = Database.getAllItemOrderByOrderID(purchaseOrder.getOrderID());
            List<BasketItem> basketItems = new ArrayList<>();
            BasketItem basketItemAux;

            boolean messageFlag = false;
            for (ItemOrder itemOrder : itemOrders) {
                int quantity = itemOrder.getQuantity();
                int productID = itemOrder.getProductID();
                Product product = Database.getProductByID(productID);
                int stock = product.getStock();

                if (stock < quantity) {
                    System.out.println("hola");
                    System.out.println(Integer.toString(stock));
                    System.out.println(Integer.toString(quantity));
                    System.out.println(Integer.toString(itemOrder.getItemID()));
                    quantity = stock;
                    itemOrder.setQuantity(quantity);
                    Database.updateItemOrder(itemOrder);
                    messageFlag = true;
                }

                basketItemAux = new BasketItem(product.getTitle(), itemOrder.getQuantity());
                basketItems.add(basketItemAux);
            }
            productsTable.getItems().setAll(basketItems);
            if (messageFlag) {
                FXUtils.showInformation("User Basket modified", "The quantity chosen for one or more products has been reduced to the maximum stock available at the moment");
            }
        }
    }
}
