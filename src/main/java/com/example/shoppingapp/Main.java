package com.example.shoppingapp;

import com.example.shoppingapp.classes.User;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

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
    * - mejorar clases y .java con sql
    * - Change suggestions of code improvement
    *
    * - limpiar codigo
    * - check previous program
    */
}
