module com.example.shoppingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing;
    requires jbcrypt;

    opens com.example.shoppingapp.controllers to javafx.fxml;
    opens com.example.shoppingapp.classes to javafx.base;
    opens com.example.shoppingapp to javafx.fxml;
    exports com.example.shoppingapp;
    opens com.example.shoppingapp.utils to javafx.fxml;
}