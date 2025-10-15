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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UserBasketController {
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> colProduct;
    @FXML
    private TableColumn<Product, String> colQuantity;

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

    }

    @FXML
    public void initialize() {
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //List<Product> products = Database.getAllProducts();
        //productsTable.getItems().setAll(products);
    }

//    @FXML
//    private void selectProduct(ActionEvent event) throws IOException {
//        Product productAux = productsTable.getSelectionModel().getSelectedItem();
//        if (productAux != null) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/shoppingapp/admin_edit_product.fxml"));
//            Parent root = loader.load();
//
//            AdminEditProductController controller = loader.getController();
//
//            int productID = productAux.getProductID();
//            Product product = Database.getProductByID(productID);
//            controller.setProduct(product, productID);
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setTitle("Produtem");
//            stage.setScene(new Scene(root));
//            stage.show();
//        } else {
//            FXUtils.showError("Selection error", "Select a product from the table");
//        }
//    }
}
