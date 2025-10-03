package com.example.shoppingapp;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 670, 425);
        stage.getIcons().add(new Image(getClass().getResource("/logo/produtem_4.png").toExternalForm()));
        stage.setTitle("Produtem");
        stage.setScene(scene);
        stage.show();
    }

    /* TODO
    * -- limpiar codigo
    * - hacer editar informacion usuario desde usuario
    *
    * - mejorar clases y .java con sql
    * - Hash password instead of String type
    * - Change suggestions of code improvement
    *
    * - check previous program
    */
}
