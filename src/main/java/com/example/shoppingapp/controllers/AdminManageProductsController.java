package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.Product;
import com.example.shoppingapp.utils.Database;
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

public class AdminManageProductsController {
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> colProductID;
    @FXML
    private TableColumn<Product, String> colTitle;
    @FXML
    private TableColumn<Product, String> colCategory;
    @FXML
    private TableColumn<Product, Float> colPrice;
    @FXML
    private TableColumn<Product, Integer> colStock;
    @FXML
    private TableColumn<Product, Byte[]> colEntryDate;

    @FXML
    void goToAdminMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/shoppingapp/admin_menu.fxml"));
        stage.setTitle("Produtem");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void initialize() {
        colProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colEntryDate.setCellValueFactory(new PropertyValueFactory<>("entryDate"));

        List<Product> products = Database.getAllProducts();
        productsTable.getItems().setAll(products);
    }

    @FXML
    private void selectProduct(ActionEvent event) throws IOException {

    }
}
